package lineanalyzer;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import javax.swing.*;

/**
 * Class the creates the line analyzer program
 * @author antho
 */
public class LineAnalyzer {

    /**
     * JTextArea to print the results
     */
    public static JTextArea resultArea = new JTextArea();

    /**
     * JTextField that allows the user to enter a number
     */
    public static JTextField userNum = new JTextField();

    /**
     * JButton to initiate the program 
     */
    public static JButton getWords = new JButton("Get Words");

    /**
     * JFrame to hold all elements used
     */
    public static JFrame frame = new JFrame();

    /**
     * a list of all the words in the document
     */
    public static List<Words> listOfWords = new ArrayList<Words>();

    /**
     * a list to hold the most used words in order
     */
    public static List<String> answer = new ArrayList<String>();

    /**
     * String to print the result
     */
    public static String numOfLines = "";

    /**
     * main method
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        lineAnalyzer();
    }

    /**
     * method that creates the program and finds the most used words 
     * @throws FileNotFoundException
     */
    public static void lineAnalyzer() throws FileNotFoundException {
        File file = new File("C:\\Users\\antho\\Downloads\\EdgarAllanPoePoem.txt");//file name
        ArrayList<String> list = new ArrayList<String>();//list to hold all words in the file

        Scanner in = new Scanner(file);//reading in the file

        while (in.hasNextLine())//loop to go over every word in the file and add it to the list
        {
            String line = in.next();

            list.add(line.toLowerCase());
        }

        for (String word : list)//going over every list element and comparing it to the listofwords
        {
            Boolean duplicate = false;

            for (Words w : listOfWords) {
                if (word.equals(w.getWord()))//if the word is a duplicate up the count of the object
                {
                    w.counterUp();
                    duplicate = true;
                    break;
                }
            }

            if (duplicate == false)//if its a new word add the word to the list of words
            {
                listOfWords.add(new Words(word));
            }
        }

        for (int i = 0; i < 20; i++) {
            answer.add(listOfWords.get(i).toString());
        }

        Collections.sort(listOfWords, Words.order);//sorting the list of words

        Frame();
    }

    /**
     *Frame class that holds the attributes for the JFrame
     */
    public static void Frame()//creation of frame
    {
        frame.setTitle("Line Searcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2));

        frame.add(new JLabel("Number of Words to get:"));
        frame.add(userNum);

        frame.add(new JLabel("Result:"));
        frame.add(resultArea);

        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        frame.add(new JLabel(""));
        frame.add(getWords);
        int frameWidth = 800;
        int frameHeight = 600;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);
        frame.setVisible(true);

        getWords.addActionListener(new ActionListener() { //action listener to get button press and send output
            public void actionPerformed(ActionEvent e) {
                int numOfWords = Integer.parseInt(userNum.getText());
                String response = "";

                for (int i = listOfWords.size() - 1; i >= listOfWords.size() - numOfWords; i--) {
                    response += listOfWords.get(i).toString() + ",  ";

                    if (i % 2 == 0) {
                        response += "\n";
                    }
                }

                resultArea.setText(response);
            }
        });
    }
}

class Words implements Comparator<Words> {

    private String word;//attributes of the object
    private int count = 0;

    public static Comparator<Words> order = new Comparator<Words>() {//comparator for the .sort function
        @Override
        public int compare(Words o1, Words o2) {
            return Integer.compare(o1.getCount(), o2.getCount());
        }
    };

    public Words(String word)//object constructor
    {
        this.word = word;
        count = 1;
    }

    public int getCount() {
        return count;
    }

    public void counterUp() {
        count += 1;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString()//overriding to string to make the output a nice value
    {
        String output = "" + word + " " + count;
        return output;
    }

    @Override
    public int compare(Words o1, Words o2) {
        return o1.getCount() - o2.getCount();
    }
}
