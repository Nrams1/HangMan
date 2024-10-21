import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class Hangman {

   JFrame window;
   JPanel panel;
   GridBagConstraints c ;
   JButton play,easy,medium,hard,newGame ,button;
   JLabel label;
   JTextField textField;
   List<ImageIcon> imageList ;
   WordList wl;
   Random x;
   Iterator<Word> iterate;
   List<Word> list ;
   ImageIcon icon;
   List<Integer> indexes;
   String underScores;
   Word mysteryWord ;
   int mysteryLen;
   int imageNo;
   StringBuilder underScore;
   String level;
   
   

   public Hangman(){
   
      play();
      frame(window);
   }
   
// method to create a frame
   public void frame( JFrame window)
   {
      this.window=window;
      window.setVisible(true); 
      window.setLocation(500,100); 
      window.setSize(500,700);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
   }
// method to create a panel
   public void panel(JPanel panel,GridBagConstraints c,Color bg)
   {
      this.panel = panel;
      this.c = c;
      panel.setBackground(bg);
      c.insets = new Insets(10,10,70,10);
   }
// method to add a button
   public JButton button(JButton button)
   {
      return button;
   }
// method to add a Label
   public void label(  JLabel label)
   {
      this.label=label;
   }
  
   
 // method to do actions button
   public void actionButton(JButton button,int NoGuesses)
   {
         
                  //Add actions to Easy Button
      button.addActionListener(
                     new ActionListener(){
                     
                        public void actionPerformed(ActionEvent e){
                        //a frame and panel 
                          
                           window.hide();  
                           frame(new JFrame("Hangman"));
                          
                           panel(new JPanel(new GridBagLayout()),new GridBagConstraints(),Color.lightGray);
                           textField = new JTextField(3);
                        
                           // convert an iterator to a list
                           iterate = wl.iterator();
                           list = new ArrayList<>(); 
                           iterate.forEachRemaining(list::add);        
                           
                           // Myster word
                           int index ;	
                           int mysteryLen;
                           
                           x = new Random(); 
                           index = x.nextInt(wl.size());
                           mysteryWord = list.get(index);
                           mysteryLen = mysteryWord.length();                    
                           
                          //Adding underscores of mysteryLen
                           underScores=" _" ;
                           for(int j=1;j<mysteryLen;j++)
                           {
                              underScores =" _" + underScores;
                           }
                           
                           
                           //Add the compents to the panel
                           icon = imageList.get(0);
                           label( new JLabel(icon) );
                           c.insets = new Insets(10,10,70,10);
                           c.gridy=1;
                           panel.add(label,c);
                           label(new JLabel(underScores)); 
                           c.gridy=2;
                           panel.add(label,c);
                           c.gridy=3;
                           panel.add(textField,c);
                           window.add(panel);
                           //No of Guesses
                             level=button.getText();
                             imageNo= NoGuesses;
                             indexes.clear();
                               
                              if(button.getText().equals("New Game")){
                                icon.setImage(new ImageIcon("state1.GIF").getImage());
                                  }
                               
                           
                          
                           textField.addActionListener(
                                 new ActionListener(){
                                 
                                    public void actionPerformed(ActionEvent e){
                                       String input = textField.getText();
                                       textField.setText("");
                                       
                                      
                                       textFieldAction(input,mysteryLen,indexes) ;  
                                   
                                                         
                                    }
                                          
                                    
                                 }); 
                           
                        } 
                     }); 
                   
   } //End of action to Button method  
           
         
   
 // method to put inputs
   public void textFieldAction(String input,int mysteryLen,List<Integer> indexes)
   {
    
      if(input.length()==1){
      
       
         input = input.toLowerCase();                                        
         Pattern inputPattern = new Pattern(input);
         Pattern mysteryPattern = new Pattern(mysteryWord.toLowerCase());
         List<Integer> check = new ArrayList<>();
                                  
                                                 
         for(int i=0;i<mysteryLen;)
         {
                                               
            int mysteryindex=mysteryPattern.indexOf(inputPattern,i);
            if(mysteryindex!=-1)
            {
              
               indexes.add(mysteryindex);
               check.add(mysteryindex);
               i=mysteryindex+1;
                                                      
            }else{
               i++; }
         }
         if(check.size()==0)
         {
             
            imageNo++;
            icon.setImage(imageList.get(imageNo).getImage());
            panel.repaint();
                                                       
         }else{
         
            underScore = new StringBuilder(underScores);
            int j ;
            for(int i=0;i<check.size();i++)
            {                                                 
               j =check.get(i);
               int k=j+1;
               {
                  underScore.setCharAt(j+k,input.charAt(0));}
            
            }
             
            indexes = indexes.stream().distinct().collect(Collectors.toList());//remove duplicate in an arrayList
            if(indexes.size()==mysteryLen)
             
            { 
                   label.setText("Congratulation you got the word  "+"\" "+ underScore.toString().replaceAll("\\s","") +" \""+"  CORRECT!");
                    newGame= button(new JButton("New Game"));
                    
                         c.gridy=4; 
                       panel.add(newGame,c);
                        window.add(panel); 
                        actionButton(newGame,  guesses(level));
                     
            }else{                                                 
               underScores = underScore.toString(); 
               label.setText(underScore.toString()); }
                                                                              
         }
         //All Guesses used UP
         if(imageNo==10)
         { 
            label.setText("SORRY!  you didn't get the word  "+"\" "+ mysteryWord.toString() +" \"");
                newGame = button(new JButton("New Game"));
                 
                c.gridy=4; 
                 panel.add(newGame,c);
                 window.add(panel); 
                 actionButton(newGame,guesses(level));
                 

         }
                                                        
      }
       
                                         
                                                
   }//end textfield method  
  
  // return No of Guesses
  public int guesses(String level)
  {
          if(level.equals("MEDIUM"))
                           {
                              imageNo= 1;
                               
                           }else if(level.equals("  HARD  "))
                           {  
                              imageNo= 2;
                                    
                           }else{ //else The buttton is easy
                                 imageNo=0;} 
                       
                       
                     return imageNo;
                 }                          
         
   public void play(){
   
   // creating a window
      frame(new JFrame("HANGMAN"));
      
   // welcome to the Game 
      panel(new JPanel(new GridBagLayout()),new GridBagConstraints(),Color.gray);
      label(new JLabel("   WELCOME   TO    HANGMAN   "));
      play = button(new JButton("PRESS HERE TO PLAY"));   
      c.gridy=1;
      panel.add(label,c);
      c.gridy=2;
      panel.add(play,c);
      window.add(panel);
        
   // Add action to the play button
      play.addActionListener(
         new ActionListener(){
         
            public void actionPerformed(ActionEvent e)
            {
               window.hide();
               frame(new JFrame("Hangman"));
            
               
             
            // Add Buttons to select Level of Difficulty   
               panel(new JPanel(new GridBagLayout()),new GridBagConstraints(),Color.gray)  ;          
               easy = button(new JButton("  EASY  "));  
               medium = button(new JButton("MEDIUM"));  
               hard = button(new JButton("  HARD  "));  
               label(new JLabel("  SELECT LEVEL   ")); 
               c.gridy=1;
               panel.add(label,c); 
               c.gridy=2;
               panel.add(easy,c);
               c.gridy=3;
               panel.add(medium,c);
               c.gridy=4 ;
               panel.add(hard,c);
               window.add(panel);
            
              //array of images
               imageList = new ArrayList<>();
               for(int i=1;i<=11;i++)
               {
                  imageList.add(new ImageIcon("state"+i+".GIF"));
               }   
            
             //Read from a File for the words 
               try{
                 
                  wl = WordList.readFromFile("dictionary.txt");
                   
               }catch(Exception error){ System.out.print(error);}
                
              
               actionButton(easy,0);
               actionButton(medium,1);
               actionButton(hard,2);
                             
                // get input from the User and take action
               indexes = new ArrayList<>();
               
            
            }
         });
   
   }//end play method
  
   public static void main(final String[] args) {  
      new Hangman();
    
   }
}