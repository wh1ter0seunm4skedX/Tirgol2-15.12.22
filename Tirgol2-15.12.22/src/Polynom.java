import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Polynom {

    private final int ZERO_VAL = 0;
    private final int ONE_VAL = 1;
    private int coefficientsLength;
    private int powersLength;
    private ArrayList<double[]> list = new ArrayList<>();

    public Polynom(double[] c,int[] p){
        Exception e;
        if (c.length!=p.length)
            e = new Exception();
        //TODO sortPowersArray()
        coefficientsLength = c.length;
        powersLength = p.length;
        for(int i=ZERO_VAL;i<c.length;i++) {
                list.add(i, new double[]{c[i], p[i]});
        }
    }

    public ArrayList<double[]> getList() {
        return list;
    }
    public void setList(ArrayList<double[]> list) {
        this.list = list;
    }
    public void setCoefficients(int coefficients) {
        this.coefficientsLength = coefficients;
    }
    public void setPowers(int powers) {
        this.powersLength = powers;
    }
    public int getCoLen() {
        return coefficientsLength;
    }
    public int getPowLen() {
        return powersLength;
    }

    private int howManyInCommon(Polynom p){
        int count = ZERO_VAL;
        for(int i=0;i<this.getList().size();i++){
            for(int j=i;j<p.getList().size();j++){
                if(this.getList().get(i)[ONE_VAL]==p.getList().get(j)[ONE_VAL]) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
    public Polynom plus1(Polynom p){
        int howManyCommonMonoms = this.howManyInCommon(p);
        double[] cArr = new double[this.getCoLen()+p.getCoLen()-howManyCommonMonoms];
        int[] pArr = new int[this.getPowLen()+p.getPowLen()-howManyCommonMonoms];
        int cArr_counter = ZERO_VAL;
        int pArr_counter = ZERO_VAL;
        boolean found = false;
        double roundDecimal = 100.0;

        for(int i=0;i<this.getCoLen();i++)
        {
            System.out.println("cArr Length is: " + cArr.length);

            System.out.println("i is: " + i);
            System.out.println("This List: " + this);
            System.out.println("P List: " + p);
            System.out.println("-----");
            for(int j=0;j<p.getCoLen();j++){
                System.out.println("pArr Length is: " + pArr.length);
                System.out.println("j is: " + j);

                if(this.getList().get(i)[ONE_VAL]==p.getList().get(j)[ONE_VAL])
                {


                    found = true;
                    cArr[cArr_counter] =  Math.round(this.getList().get(i)[ZERO_VAL]+
                            p.getList().get(i)[ZERO_VAL])*roundDecimal/roundDecimal;
                    cArr_counter++;
                    pArr[pArr_counter]= (int) this.getList().get(i)[ONE_VAL];
                    pArr_counter++;
                    break;
                }
            }
            System.out.println("end of INNER loop");
            if(!found){
                cArr[cArr_counter] =  this.getList().get(i)[ZERO_VAL]+
                        p.getList().get(i)[ZERO_VAL];
                cArr_counter++;
                pArr[pArr_counter]= (int) this.getList().get(i)[ONE_VAL];
                pArr_counter++;

            }
            found = false;

        }
        System.out.println("end of Outer loop");

        return new Polynom(cArr,pArr);
    }

/*
    public Polynom minus(Polynom p){
        double[] cArr = new double[this.getCoefficients().length+p.getCoefficients().length];
        int[] pArr = new int[this.getPowers().length+p.getPowers().length];


        return new Polynom(cArr,pArr);
    }
*/

    public Polynom derivative(){
        double[] cArr;
        int[] pArr;
        if((this.getList().get(getList().size()-1)[ONE_VAL])==ZERO_VAL) {
            pArr = new int[this.getPowLen() - 1];
            cArr = new double[this.getCoLen() - 1];
        }
        else {
            pArr = new int[this.getPowLen()];
            cArr = new double[this.getCoLen()];

        }
        //System.out.println(Arrays.toString(pArr));
        //System.out.println("pArr Length is: " + pArr.length);
        //System.out.println("cArr Length is: " + cArr.length);
        double roundDecimal = 100.0;
        for(int i=0;i<pArr.length;i++){
            if(this.getList().get(i)[ONE_VAL]!=ZERO_VAL) {
                cArr[i] = Math.round((this.getList().get(i)[ZERO_VAL]
                        * this.getList().get(i)[ONE_VAL])*roundDecimal)/roundDecimal;
                pArr[i] = (int) (this.getList().get(i)[ONE_VAL]) - 1;
                //System.out.println("cArr[" + i + "]: " + cArr[i]);
                //System.out.println("pArr[" + i + "]: " + pArr[i]);

            }
        }
        //System.out.println("Got after the loop");
        return new Polynom(cArr,pArr);
    }

    @Override
    public String toString() {
        String res = "";
        if(getList().size()==ZERO_VAL)
            return res;
        res+="[";
        for(int i=ZERO_VAL;i<getList().size();i++){
            if(i!=getList().size()-1)
                res+=getList().get(i)[ZERO_VAL] + ", " +
                        (int)getList().get(i)[ONE_VAL] + "|";
            else
                res+=getList().get(i)[ZERO_VAL] + ", " +
                        (int)getList().get(i)[ONE_VAL];
        }
        res+="]";
        return res;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    public ArrayList<double[]> test(Polynom p){
        ArrayList<double[]> temp = new ArrayList<>();
        int count = ZERO_VAL;;
        int totalLength = 2*this.getCoLen();
        for(int i=0;i<totalLength;i++){
            if(i<this.getCoLen()){
                temp.add(i,this.getList().get(i));
            }
            else if(i>=this.getCoLen()){
                temp.add(i,p.getList().get(count));
                count++;
            }
        System.out.print(Arrays.toString(temp.get(i)));
        }
        System.out.println("");
        System.out.println("-------");
        System.out.println(temp.size());
        for(int i=0;i<this.getCoLen();i++) {
            for(int j=this.getCoLen();j<totalLength;j++){
                System.out.println("Temp in (i is " + i + ") place is: " + Arrays.toString(temp.get(i)));
                System.out.println("Temp in (j is " + j + ") place is: " + Arrays.toString(temp.get(j)));

                if(temp.get(i)[ONE_VAL]==temp.get(j)[ONE_VAL]) {
                    System.out.println("Found the same power!");
                    temp.get(i)[ZERO_VAL] += temp.get(j)[ZERO_VAL];
                    temp.remove(j);
                    totalLength--;
                    break;
                }
            }
        }

        for(int i=0;i<totalLength;i++){
            System.out.print(Arrays.toString(temp.get(i)));
        }
        /**
         * SORTING THE TEMP ARRAY
         */
        for(int i=0;i<totalLength;i++) {
            for (int j = i+1; j < totalLength; j++) {
                if (temp.get(i)[ONE_VAL]<temp.get(j)[ONE_VAL]){

                    double[] tem = {temp.get(i)[ZERO_VAL], temp.get(i)[ONE_VAL]};
                    temp.set(i, new double[]{temp.get(j)[ZERO_VAL], temp.get(j)[ONE_VAL]});
                    temp.set(j, tem);

                }
            }
        }
        System.out.println("\nSorted!");

        for(int i=0;i<totalLength;i++){
            System.out.print(Arrays.toString(temp.get(i)));
        }
            return temp;
    }






    public static void main(String[] args) {
        double[] co1 = {2.8,6.5,-4.9,-12.0};
        int[] pow1 = {10,5,3,1};
        double[] co2 = {3246.03,-765.12,-93134.33,111.1};
        int[] pow2 = {12,8,5,2};
        double[] co3 = {0.12,4.5,-2.9,-2156.0};
        int[] pow3 = {11,5,3,2};
        Polynom p1 = new Polynom(co3,pow3);
        Polynom p2 = new Polynom(co2,pow2);
        //System.out.println(p1.plus(p2));
        p1.test(p2);
        //System.out.println(p1.howManyInCommon(p2));
        //System.out.println(p1.getCoLen());
        //System.out.println(p1.getPowLen());
        //System.out.println(p1.getList());
        //System.out.println(p1.derivative());


    }
}
