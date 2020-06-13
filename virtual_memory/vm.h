//
// Created by Kyle Beard on 4/24/18.
//

#ifndef HW5_VM_H
#define HW5_VM_H

// an individual page table entry
typedef struct {
  int frame; // the physical memory frame
  int valid; // whether the page has a frame, 0 for no, 1 for yes
} page_table_entry;

// an individual TLB entry
typedef struct {
  int page_num; // the page number
  int frame; // the physical frame
} tlb_entry;

// number of bytes to read
#define CHUNK               256

//number of physical frames
#define PHYSICAL_FRAMES     128

//the size of the logical address
#define ADDRESS_SIZE        10

// the number of tlb entries
#define TLB_SIZE            16

#define OFFSET_MASK 0x000000FF
#define PAGE_NUM_MASK 0x0000FF00

// physical memory used as [frame][data]
char p_mem[PHYSICAL_FRAMES][CHUNK];

// the page table
page_table_entry page_table[CHUNK];

// the TLB
tlb_entry tlb[TLB_SIZE];

FILE *address_file;
FILE *backing_store;

// how we store reads from input file
char address[ADDRESS_SIZE];

int logical_address;

// the value of the byte (signed char) in memory
signed char value;

int available_frames = PHYSICAL_FRAMES;

// number of page faults and total pt lookups
int page_faults = 0;

// number of tlb hits and lookups
int tlb_hits = 0, tlb_lookups = 0;

// rate of page faults and tlb hits
double page_fault_rate, tlb_hit_rate;


#endif //HW5_VM_H
