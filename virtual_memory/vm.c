/**
 * Input file contains logical addresses
 * 
 * Backing Store represents the file being read from disk (the backing store.)
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include "vm.h"

int main(int argc, char *argv[]) {

  int page_num; // the page number
  int offset; // the offset
  int frame_to_fill = 0; // the next frame to write into
  int frame; // the frame to read from p_mem
  int index = 0; // index used for FIFO algorithm on the TLB
  int num_addresses = 0; // the number of logical addresses used for page fault rate

  //initialize the page table
  for (int i = 0; i < CHUNK; i++) {
    page_table[i].frame = -1;
    page_table[i].valid = 0;
  }
  // initialize tlb
  for (int i = 0; i < TLB_SIZE; i++) {
    tlb[i].frame = -1;
    tlb[i].page_num = -1;
  }

  // perform basic error checking
  if (argc != 3) {
    fprintf(stderr, "Usage: ./vm [backing store] [input file]\n");
    return -1;
  }

  // open the file containing the backing store
  backing_store = fopen(argv[1], "rb");

  if (backing_store == NULL) {
    fprintf(stderr, "Error opening %s\n", argv[1]);
    return -1;
  }

  // open the file containing the logical addresses
  address_file = fopen(argv[2], "r");

  if (address_file == NULL) {
    fprintf(stderr, "Error opening %s\n", argv[2]);
    return -1;
  }

  // read through the input file and output each logical address
  while (fgets(address, CHUNK, address_file) != NULL) {
    logical_address = atoi(address);
    num_addresses++;

    // used for FIFO
    if (frame_to_fill == 128) { frame_to_fill = 0; }

    offset = logical_address & OFFSET_MASK; // get the offset
    page_num = (logical_address & PAGE_NUM_MASK) >> 8; // get the page number

    int tlb_rv = search_tlb(page_num);
    tlb_lookups++;

    // it's in the tlb, so just get the frame that the value is stored in straight away
    if (tlb_rv >= 0) {
      frame = tlb[tlb_rv].frame;
      tlb_hits++;

    } else if (page_table[page_num].valid == 1) { // not in tlb, but it is in the page table
      frame = page_table[page_num].frame;

      // update tlb
      int i = index % TLB_SIZE;
      tlb[i].page_num = page_num;
      tlb[i].frame = frame;
      index++;

    } else { //page fault
      page_faults++;
      frame = frame_to_fill;

      // no un-filled frames
      if (available_frames == 0) {
        for (int i = 0; i < CHUNK; i++) {
          if (page_table[i].frame == frame) {
            page_table[i].valid = 0;
          }
        }
      }

      //update page table
      page_table[page_num].frame = frame;
      page_table[page_num].valid = 1;

      // update tlb
      int i = index % TLB_SIZE;
      tlb[i].page_num = page_num;
      tlb[i].frame = frame;
      index++;


      // first seek to byte CHUNK in the backing store
      if (fseek(backing_store, CHUNK * page_num, SEEK_SET) != 0) {
        fprintf(stderr, "Error seeking in backing store\n");
        return -1;
      }
      if (fread(&p_mem[frame_to_fill], sizeof(signed char), CHUNK, backing_store) == 0) {
        fprintf(stderr, "Error reading from backing store\n");
        return -1;
      }

      frame_to_fill++;
      // we filled a frame, so decrement available frames unless all are already filled
      if (available_frames != 0) { available_frames--; }
    }

    value = p_mem[frame][offset];

    printf("Logical Address: %d\t Value: %d\n", logical_address, value);
  }
  printf("Page Faults: %d\n", page_faults);

  page_fault_rate = (double) page_faults / num_addresses;
  printf("Page Fault Rate: %f\n", page_fault_rate);

  printf("TLB Hits: %d\n", tlb_hits);

  tlb_hit_rate = (double) tlb_hits / tlb_lookups;
  printf("TLB Hit Rate: %f\n", tlb_hit_rate);;

  fclose(address_file);
  fclose(backing_store);

  return 0;
}

/**
 * Searches for page pageNumber in the page frame list
 * returns non-negative integer if pageNumber was found
 * returns -1 if pageNumber was not found
 */
int search_tlb(int page_num) {
  int returnVal = -1;

  for (int i = 0; i < TLB_SIZE; i++) {
    if (page_num == tlb[i].page_num) {
      returnVal = i;
      break;
    }
  }
  return returnVal;
}

