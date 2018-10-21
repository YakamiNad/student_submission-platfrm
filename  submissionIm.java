import java.util.*;
public class  submissionIm implements Submission{
	private String unikey;
	private Date date;
	private int grade;
	public  submissionIm(String unikey,Date date,Integer grade){
		this.unikey=unikey;
		this.date=date;
		this.grade=grade;
	}
	public String getUnikey(){
		return this.unikey;
	}
	public Date getTime(){
		return this.date;
	}
	public Integer getGrade(){
		return this.grade;
	}
	
}
