import java.util.Random;

public class Items {
    private String itemName; //name of item
    private int itemCount;//how many items are there
    private Random ran;
    private int yourTaken=0; // how many items are bought by you

    public int getItemCount() {//to get item count
        return itemCount;
    }

    public void setItemCount(int itemCount) {//to set item count
        this.itemCount = itemCount;
    }

    // Constructer
    public Items(int itemCount,String itemName) {
        this.itemCount = itemCount;
        ran=new Random();
        this.itemName = itemName;
    }

    // synchronized method to work one thread on same time
    public synchronized void buying(){
        int number=ran.nextInt(5);//to create random number value between 0 and 5 and other costumers buy items as this value 
        if(itemCount-number<0){ // if item count - number is less than zero, round to 0 
            itemCount=0; //describe item finish or not
        }else{
            itemCount=itemCount-number;//calculate item count - number
        }        
    }

    public String getItemName() {//get item name
        return itemName;
    }

    public void setItemName(String itemName) {//set item name
        this.itemName = itemName;
    }

    public int getYourTaken() {//get your buying number
        return yourTaken;
    }

    public void setYourTaken(int yourTaken) {//set your buying number
        this.yourTaken = yourTaken;
    }
}
