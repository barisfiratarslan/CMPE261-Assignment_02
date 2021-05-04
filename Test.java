import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Test extends JFrame implements ActionListener {

    public JPanel itemPanel;//define panel
    public Items i;//define item class for add arraylist
    public ArrayList<Items> items;//define arraylist which share with agent
    public JButton btnCreate, btnGood,btnItems; //definition button elements
    public ArrayList<JButton> itemButtons;//define arraylist because there may be a lot of button elements
    public JLabel lblNumberItems, lblNumberAgents, lblMaxTime;//definition label elements
    public JTextField txtNumberItems, txtNumberAgents, txtMaxTime;//definition text field elements
    public int differentItem=0;//count how many item created

    public Test() {
        // initiliezed arraylist
        items=new ArrayList<Items>();
        itemButtons=new ArrayList<JButton>();
        setLayout(null);

        //initialize GUI elements and add GUI
        lblNumberItems = new JLabel("Number of Items");
        lblNumberItems.setSize(140, 20);
        lblNumberItems.setLocation(30, 20);
        add(lblNumberItems);

        txtNumberItems = new JTextField();
        txtNumberItems.setSize(140, 20);
        txtNumberItems.setLocation(30, 55);
        add(txtNumberItems);

        lblNumberAgents = new JLabel("Number of Agents");
        lblNumberAgents.setSize(140, 20);
        lblNumberAgents.setLocation(200, 20);
        add(lblNumberAgents);

        txtNumberAgents = new JTextField();
        txtNumberAgents.setSize(140, 20);
        txtNumberAgents.setLocation(200, 55);
        add(txtNumberAgents);

        lblMaxTime = new JLabel("Maximum Time");
        lblMaxTime.setSize(140, 20);
        lblMaxTime.setLocation(370, 20);
        add(lblMaxTime);

        txtMaxTime = new JTextField();
        txtMaxTime.setSize(140, 20);
        txtMaxTime.setLocation(370, 55);
        add(txtMaxTime);

        btnCreate = new JButton("Create");
        btnCreate.setSize(140, 65);
        btnCreate.setLocation(540, 20);
        add(btnCreate);

        btnGood = new JButton("Good");
        btnGood.setSize(140, 65);
        btnGood.setLocation(710, 20);
        add(btnGood);

        //add action listener to buttons
        btnCreate.addActionListener(this);
        btnGood.addActionListener(this);

        btnGood.setEnabled(false);//firstly good button is not enabled because there is not any items in begin 

        //initialize jpanel
        itemPanel=new JPanel();
        itemPanel.setSize(900,700);
        itemPanel.setLocation(0,100);
        add(itemPanel);

        //describe GUI size,title,visiable and default close operation
        setSize(1000, 800);
        setTitle("E-Shopping Order Menu");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Test();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource()==btnCreate){
            try {
                int numberItems=Integer.parseInt(txtNumberItems.getText());//convert text of textfield to integer to find number of creating item
                differentItem++;// increse number of different item
                itemPanel.setLayout(new GridLayout(0,5));// set grid layout 
                String buttonName="item "+differentItem;// describe button name
                String buttonText=buttonName+", Stock: "+numberItems; // describe buuton text
                i=new Items(numberItems,buttonName);// initialize new item
                items.add(i);//add arraylist
                btnItems=new JButton(buttonText); // initialize new item button
                itemButtons.add(btnItems);//add arraylist
                btnItems.setName(buttonName); // set button name because later find this button to change text             
                itemPanel.add(btnItems); // add panel so add GUI
                itemPanel.validate(); // reflesh GUI to see changing
                btnItems.addActionListener(new ActionListener(){// add action listener to buuton
                    public void actionPerformed(ActionEvent a){// describe action performed here because every button has same name so if describe in generel action performed, just work last button
                        for (int i = 0; i < items.size(); i++) {//loop to find correct button 
                            if(items.get(i).getItemName()==buttonName){//when found correct button, increase your buying item number
                                int takenItem=items.get(i).getYourTaken()+1;
                                items.get(i).setYourTaken(takenItem);
                                if(items.get(i).getItemCount()-1>0){//control aving enough item which you want to buy and if has enough, decrease number of stock and update button text as amount of stock
                                    items.get(i).setItemCount(items.get(i).getItemCount()-1);
                                    itemButtons.get(i).setText(items.get(i).getItemName()+", Stock: "+items.get(i).getItemCount());
                                    itemPanel.validate();
                                }else{//if not having enough, show message
                                    JOptionPane.showMessageDialog(null, "There isn't enough item in stock", "Not Enough Item", JOptionPane.ERROR_MESSAGE);
                                }                    
                            }
                        }
                    }
                });       
            } catch (NumberFormatException ex) {
                //TODO: handle exception
                JOptionPane.showMessageDialog(this, "Input Integer Numbers", "Number Format Problem", JOptionPane.ERROR_MESSAGE);
            } 
            txtNumberItems.setText("");
            btnGood.setEnabled(true);
        }
        if(e.getSource()==btnGood){
            try {
                btnCreate.setEnabled(false);//prohibited adding more elements
                btnGood.setEnabled(false);//prohibited clicking again good buttons
                int numberAgents=Integer.parseInt(txtNumberAgents.getText());//convert text of textfield to integer to find number of agents
                int maxTime=Integer.parseInt(txtMaxTime.getText());//convert text of textfield to integer to find max time
                for (int i = 0; i < numberAgents; i++) {// create new costumer how many added
                    Agents a=new Agents(items, maxTime,itemPanel,itemButtons);
                    a.start();//starting added agents
                }                
            } catch (NumberFormatException ex) {
                //TODO: handle exception
                JOptionPane.showMessageDialog(this, "Input Integer Numbers", "Number Format Problem", JOptionPane.ERROR_MESSAGE);
            }            
        }
    }
}