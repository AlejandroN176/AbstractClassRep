//Alejandro Nino This programs purpose is to read in a text file of polymorphic objects of class Employee.
//Then allows the user to print information in 3 categories with an overridden method and abstract method for calculating payroll.
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
abstract class Employee {
    private String _name;
    private int _id;
    public Employee() {}
    public Employee(String s1, int x){
        _name = s1;
        _id = x;
    }
    public void setName(String n) {_name = n;}
    public void setId(int x){_id = x;}
    public String getName() {return _name;}
    public int getId(){return _id;}

    public abstract float calculatePay();
    public abstract boolean equals(Employee e);
    public String toString(){
        String builder = "";
        builder += "Name:" + _name;
        builder += " Id:" + _id;
        return builder;
    }
}
class FullTimeEmp extends Employee {
    private int _salary;
    public FullTimeEmp() {}
    public FullTimeEmp(String n, int i, int s){
        super(n, i);
        _salary = s;
    }
    public void setSalary(int s) {_salary = s;}
    public int getSalary(){return _salary;}

    public float calculatePay() { //Abstract Method
        //period is a shortened decimal form of 2 weeks out of a year
        double period = 0.038461538;
        double pay = getSalary() * period;
        float round = (float) pay;
        return round;
    }
    public boolean equals(Employee e){
        FullTimeEmp f = (FullTimeEmp) e;
        if(getName().equals(f.getName()) && getId() == f.getId()
                && getSalary() == f.getSalary())
            return true;
        else
            return false;
    }
    public String toString(){
        String builder = "";
        builder += "Name:" + super.getName();
        builder += " Id:" + super.getId();
        builder += " Salary:" + _salary;
        return builder;
    }

}

class PartTimeEmp extends Employee {
    private int _hours;
    private float _payrate;

    public PartTimeEmp(){}

    public PartTimeEmp(String s1, int i, int h , float p){
        super(s1, i);
        _hours = h;
        _payrate = p;
    }
    public void setHours(int h){_hours = h;}
    public void setPayrate(float p){ _payrate = p;}

    public int getHours(){return _hours;}
    public float getPayrate(){return _payrate;}

    public float calculatePay(){ //Abstract Method
        double pay = _hours * _payrate;
        float round = (float) pay;
        _hours = 0;
        return round;
    }
    public boolean equals(Employee e){//Abstract
        PartTimeEmp p = (PartTimeEmp) e;
        if (getName().equals(p.getName()) && getId() == p.getId()
                && getHours() == p.getHours()
                && getPayrate() == p.getPayrate()) {
            return true;
        } else {
            return false;
        }
    }

    public String toString(){//Abstract method
        String builder = "";
        builder += "Name:" + super.getName();
        builder += " Id:" + super.getId();
        builder += " Hours:" + _hours;
        builder += " Payrate:" + _payrate;
        return builder;
    }


}

class Contractor extends Employee{
    private int _completedjobs;
    private int _rate;
    public Contractor(){}
    public Contractor(String n, int i, int c, int r){
        super(n,i);
        _completedjobs = c;
        _rate = r;
    }
    public void setCompleted(int c){ _completedjobs = c;}
    public void setRate(int r){_rate = r;}
    public int getCompleted(){return _completedjobs;}
    public int getRate(){return _rate;}

    public float calculatePay(){
        double pay = _completedjobs * _rate;
        float round = (float) pay;
        _completedjobs = 0;
        return round;
    }

    public boolean equals(Employee e){//Abstract method
        Contractor c = (Contractor) e;
        if(getName().equals(c.getName()) && getId() == c.getId()
                && getCompleted() == c.getCompleted()
                && getRate() == c.getRate())
            return true;
        else
            return false;
    }

    public String toString(){//Abstract method
        String builder = "";
        builder += "Name:" + super.getName();
        builder += " Id:" + super.getId();
        builder += " CompletedProjects:" + _completedjobs;
        builder += " RatePerProject:" + _rate;
        return builder;
    }

}
public class Main {
    public static void fileRead(ArrayList employees){
        try {
            Scanner infile = new Scanner(new File("data.txt"));
            String line;
            String type;
            while(infile.hasNext()){
                line = infile.nextLine();
                String[] s = line.split(",");
                type = s[0];
                int id = Integer.valueOf(s[1]);
                if(type.equals("1")){
                    int sal = Integer.valueOf(s[3]);
                    employees.add(new FullTimeEmp(s[2], id, sal));
                }
                else if (type.equals("2")) {
                    int hours = Integer.valueOf(s[3]);
                    float rate = Float.valueOf(s[4]);
                    employees.add(new PartTimeEmp(s[2], id, hours, rate));
                }
                else {
                    int compljobs = Integer.valueOf(s[3]);
                    int rate = Integer.valueOf(s[4]);
                    employees.add(new Contractor(s[2], id, compljobs, rate));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("file Not found");
            System.exit(-1);
        }

    }

    public static void userInterface(ArrayList employees,  Scanner scan){
        System.out.println("Print out Full-time Employees payroll: 1 | Print out Part-time Employees payroll: 2");
        System.out.println("Print out Contractors payroll: 3 | Exit program: -1");
        int input = scan.nextInt();
        scan.nextLine();
        while(input != -1){
            if(input == 1){
                fullPayroll(employees);
            }
            else {if (input == 2) {
                partPayroll(employees);
            }
                else{
                    contractPayroll(employees);
                }
            }
            System.out.println("Print out Full-time Employees payroll: 1 | Print out Part-time Employees payroll: 2");
            System.out.println("Print out Contractors payroll: 3 | Exit program: -1");
            input = scan.nextInt();
            scan.nextLine();
        }
    }
    public static void fullPayroll(ArrayList employees){
        int totalpay = 0;
        for(int i =0; i< employees.size(); i++){
            if(employees.get(i) instanceof FullTimeEmp){
                FullTimeEmp full = (FullTimeEmp) employees.get(i);
                int pay = (int) full.calculatePay();
                totalpay += pay;
                System.out.println(full.toString() + " Pay:" + pay);
            }
        }
        System.out.println();
        System.out.println("Total Payout:" + totalpay);
        System.out.println();
    }
    public static void partPayroll(ArrayList employees){
        int totalpay = 0;
        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i) instanceof PartTimeEmp){
                PartTimeEmp part = (PartTimeEmp) employees.get(i);
                System.out.print(part.toString());
                int pay = (int) part.calculatePay();
                totalpay += pay;
                System.out.print("  Pay:" + pay + "\n");
            }
        }
        System.out.println();
        System.out.println("Total Payout: " + totalpay);
        System.out.println("Calculated pay out , setting hours work to 0.");
        System.out.println();

    }
    public static void contractPayroll(ArrayList employees){
        int totalpay = 0;
        for(int i = 0; i < employees.size(); i++){
            if(employees.get(i) instanceof Contractor) {
                Contractor con = (Contractor) employees.get(i);
                System.out.print(con.toString());
                int pay = (int) con.calculatePay();
                totalpay += pay;
                System.out.print(" Pay:" + pay + "\n");

            }
        }
        System.out.println();
        System.out.println("Total Payout:" + totalpay);
        System.out.println("Calculated contractor pay, setting completed projects to 0.");
        System.out.println();
    }
    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        fileRead(employees);
        userInterface(employees, scan);
        System.out.println("Program Ending...");
    }
}