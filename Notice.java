import javax.swing.JLabel;
import javax.swing.JPanel;


public class Notice {
	public static Notice note= null;
	
	JPanel noticeBoard;
	JLabel notice;
	private Notice()
	{
		noticeBoard=new JPanel();
		noticeBoard.setSize(50, 600);
		
		notice= new JLabel("Notifications :");
		noticeBoard.add(notice);
	}
	
	public JPanel getNoticeBoard()
	{
		return noticeBoard;
	}
	
	public void setText(String text)
	{
		notice.setText("Notification :"+text);
	}
	
	public static Notice getInstance()
	{
		if(note == null)
		{
			note= new Notice();
		}
		return note;
	}
	
}