package book.bruceeckel.finalize.test;


public class Book {
    boolean checkedOut = false;

    public Book(boolean checkOut) {
        checkedOut = checkOut;
    }
    
    public void checkIn(){
        checkedOut = false;
    }
    
    @Override
    public void finalize() throws Throwable{
        if (!checkedOut){
            System.out.println("Ошибка: checkedOut");
            super.finalize();   
        }
    }
    

}
