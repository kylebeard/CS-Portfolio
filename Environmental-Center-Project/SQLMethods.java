
/*
Contains all the methods for querying the database 
*/

public class SQLMethods {

  public SQLMethods(){}


  public String selectAllInvoicesByAccount(int accountNum){
    String query = "Select * from Invoice where accountNum = " + accountNum + ";";
    return query;
  }

  public String selectAccount(int accountNum){
    String query = "Select * from Account where accountNum = " + accountNum + ";";
    return query;
  }

  /**
   * Method to delete a row in Invoice
   */
  public String deleteInvoice(int invoiceNum){
    String query = "Delete From Account where invoiceNum = " + invoiceNum +";";
    return query;
  }

  /**
   * Method to delete a row in Invoice
   */
  public String deleteAccount(int accountNum){
    String query = "Delete From Invoice where invoiceNum = " + accountNum +";";
    return query;
  }

  /**
   * startYear and endYear need to be YYYY
   * returns total water usage in gallons and total spending in a fiscal year
   */
  public String waterUsedInFiscalYear(int year){
    // int year = startYear;
    // String[] queries = new String[(endYear-startYear) + 1];

    // for(int i = 0; i <= (endYear - startYear); i++){
    //   // increment the year each iteration
    //   year += 1;

      //format the query
      String query = "Select sum(billingUnits*748) AS gallons, sum(totalCharges) From Invoice Where serviceFrom >= '" + year + 
      "-07-01' and serviceFrom <= '" + (year+1) + "-06-30';";

      // add it to the array
      // queries[i] = query;  
    // }

    return query;
  }

   /**
   * startYear and endYear need to be YYYY
   * returns total water usage by account in gallons and total spending in a fiscal year by account
   */
  public String waterUsedInFiscalYearByAccount(int year, int accountNum){
    // int year = startYear;
    // String[] queries = new String[(endYear-startYear) + 1];

    // for(int i = 0; i <= (endYear - startYear); i++){
    //   // increment the year each iteration
    //   year += 1;

      //format the query
      String query = "Select accountNum, sum(billingUnits*748) AS gallons, sum(totalCharges) From Invoice Where serviceFrom >= '" + year + 
      "-07-01' and serviceFrom <= '" + (year+1) + "-06-30' and accountNum = " + accountNum + ";"; 

      // add it to the array
      // queries[i] = query;
    // }

    return query;

  }

   /**
   * startYear and endYear need to be YYYY, month is in the integer value
   * compares water usage and total charges for a certain month in the range between startYear and endYear (water is in gallons) 
   */
  public String waterUsedByMonth(int year, int month){
    // int year = startYear;
    // String[] queries = new String[(endYear-startYear) + 1];

    // get the string representation to use in query
    String strMonth = Integer.toString(month);
    if(month < 10){
      strMonth = "0" + strMonth;
    }

    // for(int i = 0; i <= (endYear - startYear); i++){
    //   // increment the year each iteration
    //   year += 1;

      //format the query
      String query = 
      "SELECT strftime('%m', serviceFrom) as month, sum(billingUnits*748) AS gallons, SUM(totalCharges) " +
      "FROM Invoice " +
      "WHERE strftime(“%m”, serviceFrom) = '" + strMonth + "' AND strftime(“%Y”, serviceFrom) = '" + year + "';"; 

      // add it to the array
      // queries[i] = query;
  
  // }
  return query;
}

 /**
   * startYear and endYear need to be YYYY, month is in the integer value
   * compares water usage and total charges for a certain month in the range between startYear and endYear (water is in gallons) 
   */
  public String[] waterUsedByMonthByAccount(int startYear, int endYear, int month, int accountNum){
    int year = startYear;
    String[] queries = new String[(endYear-startYear) + 1];

    // get the string representation to use in query
    String strMonth = Integer.toString(month);
    if(month < 10){
      strMonth = "0" + strMonth;
    }

    for(int i = 0; i <= (endYear - startYear); i++){
      // increment the year each iteration
      year += 1;

      //format the query
      String query = 
      "SELECT sum(billingUnits*748) AS gallons, SUM(totalCharges) " +
      "FROM Invoice " +
      "WHERE strftime(“%m”, serviceFrom) = '" + strMonth + "' AND strftime(“%Y”, serviceFrom) = '" + year + 
      "' and accountNum = " + accountNum + ";"; 

      // add it to the array
      queries[i] = query;
  
  }
  return queries;
}

  // public static void main(String[] args){
  //   SQLMethods sql = new SQLMethods();
  //   String[] values = sql.waterUsedByMonthByAccount(2012,2017,6,12345);
  //   for(int i = 0; i < values.length; i++){
  //     System.out.println(values[i]);
  //   }
  // }


}