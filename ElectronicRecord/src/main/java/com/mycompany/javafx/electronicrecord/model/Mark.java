package com.mycompany.javafx.electronicrecord.model;


public enum  Mark {
    Passed(1, "Зачет"), notPassed(0, "Незачет");
 
   private int code;
   private String text;
 
   private Mark(int code, String text) {
       this.code = code;
       this.text = text;
   }
 
   public int getCode() {
       return code;
   }
 
   public String getText() {
       return text;
   }
 
   public static Mark getByCode(int genderCode) {
       for (Mark g : Mark.values()) {
           if (g.code == genderCode) {
               return g;
           }
       }
       return null;
   }
 
   @Override
   public String toString() {
       return this.text;
   }

}
