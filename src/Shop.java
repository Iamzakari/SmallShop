/**
 * Created by ZAKARI on 18.06.2017.
 */

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Shop {

    int maxgoods = 20; //columns
    int good_charachteres = 30;
    int row_numbers = 1000; // rows

    public static void main(String[] args) {

        // use HashMap to contain items and their value
        HashMap<String, Integer> itemMap = new HashMap<String, Integer>();

        //use array list that contain another array list (Customeers and itemms)
        ArrayList<ArrayList<Item>> customerList = new ArrayList<ArrayList<Item>>();

        try {
            PrintStream output = new PrintStream(new FileOutputStream("osszeg.txt", true));
            System.setOut(output);
            //read the file from the text and process its contens
            File file = new File("penztar.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            //take lines and
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String itemName = line.trim();
                // when the line correspond to F
                //F” means that the customer doesn’t have a new goods in his cart
                //F separate customers
                if(itemName.equals("F")) {
                    ArrayList<Item> itemList = new ArrayList<Item>();
                    for(String key : itemMap.keySet()) {
                        itemList.add(new Item(key, itemMap.get(key)));
                    }
                    customerList.add(itemList);

                    itemMap = new HashMap<String, Integer>();
                    continue;
                }
                if(!itemMap.containsKey(itemName)) {
                    itemMap.put(itemName, 1);
                }
                else {
                    itemMap.put(itemName, itemMap.get(itemName) + 1);
                }

            }
            fileReader.close();
         //Throw exception if there is no file to read
        } catch (IOException e) {
            e.printStackTrace();
        }

      //file write in
        System.out.println("file contents:\n");
        for(int i=0;i<customerList.size();i++) {

            double price1 = 0.0, price2 = 0.0, price3 = 0.0,  price =0.0;

            for(Item item : customerList.get(i)) {
              //500 HUF/piece
                if((item.quantity == 1)){
                    price1 += 500;
                }
                //then the 2nd is only 450 HUF
                else if((item.quantity == 2))
                    price2 += 500 + 450;
                  //the 3rd piece is just 400 HUF, and the 4th, 5th..... are the prices after the 3rd one will not decrease.
                else{
                    price3 += 500+ 450 + 400 + 400 *((item.quantity)-3);
                }

                price = price1 + price2 + price3;
                System.out.println(item.itemName + " : " + item.quantity);
            }

            System.out.println("The total price for customer number: " + (i+1) + "  is: "  + price + " HUF\n");
            System.out.println("--------------------------------------------------------");
        }


        //print the number of payement which is number of customers
        System.out.println("2.Define how many times were paid at the cashier\n");
        System.out.println( customerList.size() + "  times were paid at the cashier");

    }
}
