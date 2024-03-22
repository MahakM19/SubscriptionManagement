//creating student data management project
//using file handling and 
//command line arguments
import java.io.*;
class StudentManagement
{
static final String operations[] = {"ADD", "UPDATE", "REMOVE", "LIST", "LISTBYCOURSE", "SEARCH"};
static final String courses[] = {"C", "C++", "Java", "Python"};
public static void main(String args[])
{
if(args.length==0)
{
System.out.println("No arguments passed! EXIT");
return;
}
String operation=args[0].toUpperCase();
if(!isOperationValid(operation))
{
System.out.print("Operation is not valid. Valid operations are:");
for(int i=0;i<operations.length;i++)System.out.print(operations[i]+"\t");
}
switch(operation)
{
case "ADD":addStudent(args); break;
case "UPDATE": updateStudent(args); break;
case "REMOVE": removeStudent(args); break;
case "LIST": displayStudents(args); break;
case "LISTBYCOURSE": displayStudentsByCourse(args); break;
case "SEARCH": searchStudent(args); break;
default: return;//this condition never gonna come
}
}//main
private static boolean isOperationValid(String o)//static because main is static
{
for(int i=0;i<operations.length;i++)
{
if(operations[i].equalsIgnoreCase(o))return true;//or we can simply user equals as well because in main we are converting it to uppercase
}
return false;
}
private static boolean isCourseValid(String c)
{
for(int i=0;i<courses.length;i++)
{
if(courses[i].equalsIgnoreCase(c))return true;
}
return false;
}
private static boolean contactExists(String contact)
{
try
{
File file = new File("students.data");
if(!file.exists()) return false;
RandomAccessFile f = new RandomAccessFile(file,"r");
if(f.length()==0) 
{
f.close(); 
return false;
}
String c;
while(f.getFilePointer()<f.length())
{
c=f.readLine();
if(c.equalsIgnoreCase(contact))return true;
c=f.readLine();
c=f.readLine();
c=f.readLine();
}
f.close();
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
return false;
}
private static void addStudent(String[] data)
{
if(data.length!=5)
{
System.out.println("Invalid number of input to add student details, Correct format is >java StudentManagement ADD [ContactNumber] [StudentName] [CourseName] [Fees]");
return;
}
String contactNumber = data[1];
if(contactExists(contactNumber))
{
System.out.println("Contact already exists!\n EXIT");
return;
}
String name = data[2];
String course = data[3];
if(!isCourseValid(course))
{
System.out.print("Course is not valid. Valid course options are:");
for(int i=0;i<courses.length;i++)System.out.print(courses[i]+"\t");
}
int fees=0;
try
{
fees = Integer.parseInt(data[4]);
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
try
{
File file = new File("students.data");
RandomAccessFile f = new RandomAccessFile(file,"rw");
while(f.getFilePointer()<f.length())
{
f.readLine();
f.readLine();
f.readLine();
f.readLine();
}
f.writeBytes(contactNumber+"\n"+name+"\n"+course+"\n"+String.valueOf(fees)+"\n");
f.close();
System.out.println("new student details added!");
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
private static void updateStudent(String[] data)
{
if(data.length!=5)
{
System.out.println("Invalid number of input to update student details, Correct format is >java StudentManagement UPDATE [ContactNumber] [StudentName] [CourseName] [Fees]");
return;
}
if(!contactExists(data[1]))
{
System.out.println("Contact doesnot exists! EXIT");
return;
}
String contactNumber=data[1];
String name=data[2];
if(!isCourseValid(data[3]))
{
System.out.print("Course is not valid. Valid course options are:");
for(int i=0;i<courses.length;i++)System.out.print(courses[i]+"\t");
}
String course=data[3];
int fees=0;
try
{
fees = Integer.parseInt(data[4]);
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
try
{
File fileO = new File("Students.data");
File fileT = new File("Temp.data");
RandomAccessFile f1 = new RandomAccessFile(fileO, "rw");
RandomAccessFile f2 = new RandomAccessFile(fileT, "rw");
String contactNumber1, name1, course1, fees1;
while(f1.getFilePointer()<f1.length())
{
contactNumber1=f1.readLine();
name1=f1.readLine();
course1=f1.readLine();
fees1=f1.readLine();
if(contactNumber1.equalsIgnoreCase(contactNumber))
{
f2.writeBytes(contactNumber+"\n"+name+"\n"+course+"\n"+String.valueOf(fees)+"\n");
continue;
}
f2.writeBytes(contactNumber1+"\n"+name1+"\n"+course1+"\n"+fees1+"\n");
}
//copying to correct data to original file
f1.seek(0);//taking the internal file pointer of original file to 0
f2.seek(0);
while(f2.getFilePointer()<f2.length()) f1.writeBytes(f2.readLine()+"\n");
f1.setLength(f2.length());
f1.close();
f2.close();
fileT.delete();
System.out.println("Done");
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
private static void removeStudent(String[] data)
{
if(data.length!=2)
{
System.out.println("Invalid number of input to remove a student's details from the file, Correct format is >java StudentManagement REMOVE [ContactNumber]");
return;
}
if(!contactExists(data[1]))
{
System.out.println("Contact doesnot exists! EXIT");
return;
}
String contactNumber=data[1];
try
{
File fileO = new File("Students.data");
File fileT = new File("Temp.data");
RandomAccessFile f1 = new RandomAccessFile(fileO, "rw");
RandomAccessFile f2 = new RandomAccessFile(fileT, "rw");
String contactNumber1, name1, course1, fees1;
while(f1.getFilePointer()<f1.length())
{
contactNumber1=f1.readLine();
name1=f1.readLine();
course1=f1.readLine();
fees1=f1.readLine();
if(contactNumber1.equalsIgnoreCase(contactNumber)) continue;
f2.writeBytes(contactNumber1+"\n"+name1+"\n"+course1+"\n"+fees1+"\n");
}
//copying to correct data to original file
f1.seek(0);//taking the internal file pointer of original file to 0
f2.seek(0);
while(f2.getFilePointer()<f2.length()) f1.writeBytes(f2.readLine()+"\n");
f1.setLength(f2.length());
System.out.println(f1.length()+"\t"+f2.length());
f1.close();
f2.close();
fileT.delete();
System.out.println("Done");
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
private static void displayStudents(String[] data)
{
if(data.length!=1)
{
System.out.println("Invalid number of input to show all students' details, Correct format is >java StudentManagement LIST");
return;
}
try
{
File file = new File("students.data");
if(!file.exists()) return;
RandomAccessFile f = new RandomAccessFile(file,"r");
if(f.length()==0) 
{
f.close(); 
return;
}
while(f.getFilePointer()<f.length())
{
System.out.println("CONTACT NUMBER: "+f.readLine()+"\tNAME: "+f.readLine()+"\tCOURSE: "+f.readLine()+"\tFEES: "+f.readLine());
}
f.close();
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
private static void displayStudentsByCourse(String[] data)
{
if(data.length!=2)
{
System.out.println("Invalid number of input to show all students' details enrolled in a particular couse, Correct format is >java StudentManagement LISTBYCOURSE [CourseName]");
return;
}
if(!isCourseValid(data[1]))
{
System.out.print("Course is not valid. Valid course options are:");
for(int i=0;i<courses.length;i++)System.out.print(courses[i]+"\t");
return;
}
try
{
File file = new File("students.data");
if(!file.exists()) return;
RandomAccessFile f = new RandomAccessFile(file,"r");
if(f.length()==0) 
{
f.close(); 
return;
}
String contactNumber, name, course;
int fees, count=0;
while(f.getFilePointer()<f.length())
{
contactNumber=f.readLine();
name=f.readLine();
course=f.readLine();
fees=Integer.parseInt(f.readLine());
if(course.equalsIgnoreCase(data[1]))
{
System.out.println("CONTACT NUMBER: "+contactNumber+"\tNAME: "+name+"\tCOURSE: "+course+"\tFEES: "+fees);
count++;
}
}
f.close();
System.out.println("Total registered students for ["+data[1].toUpperCase()+"] are "+count);
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
private static void searchStudent(String[] data)
{
if(data.length!=2)
{
System.out.println("Invalid number of input to show a student's details, Correct format is >java StudentManagement SEARCH [ContactNumber]");
return;
}
try
{
File file = new File("Students.data");
if(!file.exists()) return;
RandomAccessFile f = new RandomAccessFile(file,"r");
if(f.length()==0)
{
f.close(); 
return;
}
String contactNumber;
while(f.getFilePointer()<f.length())
{
contactNumber=f.readLine();
if(contactNumber.equalsIgnoreCase(data[1]))
{
System.out.println("CONTACT NUMBER: "+contactNumber+"\tNAME: "+f.readLine()+"\tCOURSE: "+f.readLine()+"\tFEES: "+f.readLine());
f.close();
return;
}
else
{
f.readLine();
f.readLine();
f.readLine();
}
}
System.out.println("No student exists with this contact number! EXIT.");
f.close();
}
catch(Exception e)
{
System.out.println(e.getMessage());
}
}
}