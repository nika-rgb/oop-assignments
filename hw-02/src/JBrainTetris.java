import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class JBrainTetris  extends JTetris{
    private DefaultBrain brain;
    private JCheckBox brainMode;
    private JCheckBox animate;
    private int newCount;
    private Brain.Move currentBest;
    private  JSlider adversary;
    private JLabel status;
    private Random rnd;
    private Piece [] basicPieces;
    private Brain.Move worstEachPiece;
    /**
     * Creates a new JTetris where each tetris square
     * is drawn with the given number of pixels.
     *
     * @param pixels
     */
    JBrainTetris(int pixels) {
        super(pixels);
        brain = new DefaultBrain();
        newCount = 0;
        currentBest = new Brain.Move();
        rnd = new Random();
        basicPieces = Piece.getPieces();
        worstEachPiece = new Brain.Move();
    }


    @Override
    public JComponent createControlPanel(){
        JComponent panel = super.createControlPanel();
        brainMode = new JCheckBox("Brain");
        brainMode.setSelected(false);
        brainMode.setVisible(true);
        panel.add(brainMode);

        animate = new JCheckBox("Animate Falling");
        animate.setSelected(true);
        panel.add(animate);


        JPanel newPanel = new JPanel();
        newPanel.add(new JLabel("Adversary:"));
        adversary = new JSlider(0, 100, 0);
        adversary.setPreferredSize(new Dimension(100, 15));
        newPanel.add(adversary);
        status = new JLabel("ok");
        panel.add(newPanel);
        panel.add(status);

        return panel;
   }


    @Override
    public void tick(int verb) {
        if(brainMode.isSelected() && verb == DOWN){
            if(newCount != count){
                newCount = count;
                board.undo();
                brain.bestMove(board, currentPiece, HEIGHT, currentBest);
            }
            if(currentBest != null){
                int x = currentBest.x;
                int y = currentBest.y;


                if (!currentPiece.equals(currentBest.piece))
                    currentPiece = currentPiece.fastRotation();

                if(x > currentX) currentX++;
                else if(x < currentX) currentX--;

                if(!animate.isSelected() && currentX == x && currentY != y && currentPiece.equals(currentBest.piece))
                    verb = DROP;


            }

        }
       super.tick(verb);
    }

    @Override
    public Piece pickNextPiece() {
        int rnd_number = rnd.nextInt(98) + 1;
        Piece curr = null;
        if(rnd_number < adversary.getValue()){
            status.setText("*ok*");
            double largestScore = Integer.MIN_VALUE;
            for(int k = 0; k < basicPieces.length; k++){
                brain.bestMove(board, basicPieces[k], HEIGHT, worstEachPiece);
                if(worstEachPiece == null) continue;
                if(largestScore < worstEachPiece.score){
                    largestScore = worstEachPiece.score;
                    curr = basicPieces[k];
                }

            }
        }else{
           status.setText("ok");
            curr = super.pickNextPiece();
        }

        if(curr == null)
            return super.pickNextPiece();

        return curr;
    }

    public static void main(String [] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        JBrainTetris jBrain = new JBrainTetris(16);
        JFrame frame = jBrain.createFrame(jBrain);
        frame.setVisible(true);
    }
}


