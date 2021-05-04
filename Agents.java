import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Agents extends Thread {
    private ArrayList<Items> items;//arraylist of items
    private int maxWaitingTime;// it describe maximum waiting time for thread 
    private Random ran;
    private JPanel itemPanel; // to reflesh gui for seeing changing text of button
    private ArrayList<JButton> itemButtons; // to take button list from test
    private ArrayList<Integer> finishItemCount; // Arraylist of finishing items. it helps me to find which items is finished

    // Constructer
    public Agents(ArrayList<Items> items, int maxWaitingTime,JPanel itemPanel,ArrayList<JButton> itemButtons) {
        this.items = items;
        this.maxWaitingTime = maxWaitingTime;
        ran = new Random();
        this.itemPanel=itemPanel;
        this.itemButtons=itemButtons;
        finishItemCount=new ArrayList<Integer>();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        for(;;){ // loop will are works all items sold
            try {
                String text=""; //initiaze text for message diolog
                int index=ran.nextInt(items.size());// create a random number between 0 and number of items
                if(finishItemCount.size()==items.size()){//to find all item is finished if it finish, show message which contains your buying count of items
                    for (int i = 0; i < items.size(); i++) {// loop will write all items and your buying number of items
                        if(items.get(i).getYourTaken()!=0){// to find item is bought or not
                            text=text+items.get(i).getItemName()+": "+items.get(i).getYourTaken()+"\n";
                        }                        
                    }             
                    JOptionPane.showMessageDialog(null, text+"Do you want to buy?", "Your Basket", JOptionPane.INFORMATION_MESSAGE,null);                    
                    System.exit(0);//if you click ok, will close the program
                }
                if(finishItemCount.contains(index)){// to find item which is finished in creating number 

                }else{
                    if(items.get(index).getItemCount()==0){//to find item is finished
                        itemButtons.get(index).setText(items.get(index).getItemName()+", No Stock");//to set no stock of  text of buttons
                        itemButtons.get(index).setEnabled(false);// to prohibit clik after finishing stock
                        itemPanel.validate();//to reflesh gui
                        finishItemCount.add(index);// add arraylist
                    } else{
                        items.get(index).buying();// other costumers buy items
                        itemButtons.get(index).setText(items.get(index).getItemName()+", Stock: "+items.get(index).getItemCount());//to change amount of stock in buttons
                        itemPanel.validate();//to reflesh gui
                    }
                }            
                sleep(ran.nextInt(maxWaitingTime));//thread sleeps between 0 and maximum time     
            } catch (InterruptedException e) {
                //TODO: handle exception
                System.out.println("Agents: "+e.getMessage());//if having any error, showing this message
            }
        }
    }
}
