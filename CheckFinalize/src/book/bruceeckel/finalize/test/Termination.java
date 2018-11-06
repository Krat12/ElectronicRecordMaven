/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.bruceeckel.finalize.test;

/**
 *
 * @author Admin
 */
 class Termination {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Book novel = new Book(true);   
        novel.checkIn();
        novel = null;
        // new Book(true);
        System.gc();
    }
    
}
