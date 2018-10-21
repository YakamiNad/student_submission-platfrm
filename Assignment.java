import java.util.Date;
import java.util.List;
import java.util.*;

public class Assignment implements SubmissionHistory {
	/**
	 * Default constructor
	 */
	//overall statement:
	// i USE the treemap to implements all the method except the one with the listtopstudents where i use the hashset method 
	//in order to delete the repeated value
      
	
	  submissionIm pre;//create a submit object used for later use in add method;
	 ArrayList<Submission> sub2=new ArrayList<Submission>();
	 TreeMap<String, TreeMap<Integer, Submission>> treemap;
	TreeMap<String, TreeMap<Date, Submission>>treemap1;
		// TODO initialise your data structures
	public Assignment() { 
		treemap=new TreeMap<String, TreeMap<Integer, Submission>>();
		treemap1=new TreeMap<String, TreeMap<Date, Submission>>();
	}
	//created two trees for appropirate use in different methods
	
	
    
	@Override
	public Integer getBestGrade(String unikey) {
		// TODO Implement this, ideally in better than O(n)
		//the treemap varible is the tree(String,treemap(grade,submission object))
		//return the last key of is return the highest grade
		
		if(unikey==null)
		{
			throw new IllegalArgumentException();
		}
		else if(treemap.get(unikey)!=null)
		{
		  return  treemap.get(unikey).lastKey();
		}
		  return null;
		
	}
	

	
	

	@Override
	public Submission getSubmissionFinal(String unikey) {
		// TODO Implement this, ideally in better than O(n)
		//my treemap1(which is different from treemap) is treemap1(String unikey,treemap(date,submission))
		//so the same,the lastKey()is just the last time;
		
		if(unikey==null)
		{
			throw new IllegalArgumentException();
		}
		else if(treemap1.get(unikey)!=null)
		{
			return treemap1.get(unikey).get(treemap1.get(unikey).lastKey()); 
		}
		return null;
	}

	@Override
	public Submission getSubmissionBefore(String unikey, Date deadline) {
		// TODO Implement this, ideally in better than O(n)
		
		
		//my treemap1(which is different from treemap) is treemap1(String unikey,treemap(date,submission))
		//Unlike lastkey,floor key method is really helpful in this case
		
		if(unikey==null|| deadline==null)
		{
			throw new IllegalArgumentException();
		}
		else if((treemap1.get(unikey).floorKey(deadline))!=null)
		{ 
			return treemap1.get(unikey).get(treemap1.get(unikey).floorKey(deadline));
		}
		
		return null;	
	}

	@Override
	//for add method,i store the sunmission object data in two trees which is treemap and treemap1.
	//to do this,can call the method as well as calling the data more conveniently
	
	public Submission add(String unikey, Date timestamp, Integer grade) {
		if(unikey==null || timestamp==null ||grade==null){
			throw new IllegalArgumentException();
		}
		else{
			 pre=new Submit(unikey,timestamp,grade);
		
			if(treemap.containsKey(unikey))
			{
				treemap.get(unikey).put(grade,pre);//simply put more elements in the nestedtreemap and will be updated to the main treemap automatically
			}
			if(treemap.containsKey(unikey)==false)
			{
				TreeMap<Integer,Submission> map2=new TreeMap<Integer,Submission>();
				////once i found that the new unikey being added,i will immediately create a new entry for the main treemap
				treemap.put(unikey,map2);
				map2.put(grade,pre);
			}
			
			if(treemap1.containsKey(unikey)==true)
			{
				treemap1.get(unikey).put(timestamp,pre);
				return pre;
			}
			else
			{
				////once i found that the new unikey being added,i will immediately create a new entry for the main treemap
				TreeMap<Date,Submission> map=new TreeMap<Date,Submission>();
				treemap1.put(unikey,map);
				map.put(timestamp,pre);
			}
			
		}
		// TODO Implement this, ideally in better than O(n)
		return pre;
	}

	@Override
	public void remove(Submission submission) {
		// TODO Implement this, ideally in better than O(n)
		if(submission==null)
		{	
			throw new IllegalArgumentException();
		}
	
 
		String uni=submission.getUnikey();
		int grade=submission.getGrade();
		Date time=submission.getTime();
		//as i am storing the date and grade in different treemaps,and aslo the method before might need to call two trees in one method
		//so in order to make sure that i remove it throughly,i need to make sure the submission object to be removed in one treemap is the actual one;
		//as one student can has the same grade but different time,so i should call the getTime() to confirm the date again once i confirm the grade; 
		if(treemap.get(uni).get(grade).getTime().equals(time))
		{ 
		  treemap.get(uni).remove(grade);
		}
		
		
	}
	@Override
	//the brief logic for this one is:
	//1.find the best grades in every single students;
	//2.compare each single student's best grades; 
	public List<String> listTopStudents() {
		// TODO Implement this, ideally in better than O(n)
		// (you may ignore the length of the list in the analysis)
		
		
		//this is for the final highest mark which is the listTopStudents
		List<String> top=new ArrayList<String>();
		//this is for storing every single students's best mark	
		List<Submission> top1=new ArrayList<Submission>();
		
		// As i am using the key to store each student's several submission,i have to go through the treemap
		// then using the lastkey() method to find the best mark this student has and store it with the Submission object type
		// As i need to return the name,so i choose to store them with the submission type;
		 Set keys = treemap.keySet();
   for (Iterator i = keys.iterator(); i.hasNext();) {
     String key = (String) i.next();
     TreeMap<Integer,Submission> value = (TreeMap<Integer,Submission>) treemap.get(key);
     top1.add(value.get(value.lastKey()));
   }
		//now start to compare each students's best marks,which should be basic staff we learn in the pre-knowledge course info1103;
		int size=top1.size();
		if(size==0){
			return top;
		}
		int max=top1.get(0).getGrade();
		top.add(top1.get(0).getUnikey());
		for(int i=1;i<size;i++){
			if(max<top1.get(i).getGrade())
			{				
				max=top1.get(i).getGrade();
				top.clear();
				top.add(top1.get(i).getUnikey());
			}
			if(max==top1.get(i).getGrade())
			{
				top.add(top1.get(i).getUnikey());
			}
			if(max>top1.get(i).getGrade())
			{
				continue;
			}
		}
		//By now,we have all the best students's mark,As the students might be the same as the one student could sumbit the best mark twice
		//So i choose to use HashSet to check whether there are students with the same name
		//As the hashset property wont allow the repeated value in a set;
          Set<String> all = new HashSet<>();
          all.addAll(top);
          top.clear();
          top.addAll(all);
		  return top;
	}
	

	@Override
	//the logic for this method is i need to go over each student,which is everykey;
    //I use the iterator to go through every key and call the method getbefore and bestGrade which are really useful
	public List<String> listRegressions() {
		// TODO Implement this, ideally in better than O(n^2)
		List<String> top1=new ArrayList<String>();
		//The following comment is another way to achieve the effect
		
		// TreeMap<String, TreeMap<Integer, Submission>> treemapnew=new TreeMap<String, TreeMap<Integer, Submission>>();
		// treemapnew=(TreeMap)treemap.clone();
		// while(treemapnew.firstEntry()!=null){
		// 	String s=treemapnew.firstEntry().getKey();
		// 	treemapnew.pollFirstEntry();
		// 	if(getSubmissionFinal(s).getGrade()<getBestGrade(s))
		// 	{
		// 		top1.add(s); 
		// 	}
		// }
		 Set keys = treemap.keySet();
		
		for (Iterator i = keys.iterator(); i.hasNext();) {
          String key = (String) i.next();
		  if(getSubmissionFinal(key).getGrade()<getBestGrade(key))
		 	{
				top1.add(key); 
		 	}
		}
		return top1;
	}
}
