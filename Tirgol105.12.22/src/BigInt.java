import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.ZipEntry;

public class BigInt implements Comparable<BigInt> {
    private final int DEF_VAL = 0;
    private ArrayList<String> list;
    private boolean POSITIVE = false;
    private boolean NEGATIVE = false;

    public BigInt(String str) {
        list = new ArrayList<>(str.length());
        if (str.charAt(0) == '+') {
            POSITIVE = true;
            list.add("+");
        } else if (str.charAt(0) == '-') {
            NEGATIVE = true;
            list.add("-");
        }
        if (POSITIVE || NEGATIVE)
            for (int i = 1; i < str.length(); i++) {
                list.add(i, str.substring(i, i + 1));
            }


    }
    public ArrayList<String> getBig() {
        return list;
    }
    public void setList(ArrayList<String> list) {
        list.clear();
        this.list = list;
    }
    public void setList(String str){
        list.clear();
        for(int i=0;i<str.length()-1;i++){
            list.add(DEF_VAL,str.substring(i,i+1));
        }
    }
    private boolean isNEGATIVE() {
        return NEGATIVE;
    }
    private boolean isPOSITIVE() {
        return POSITIVE;
    }
    private String PositiveSign() {
        return "+";
    }
    private String NegativeSign() {
        return "-";
    }
    private boolean isTheSameSign(BigInt param) {
        return (this.isPOSITIVE() && param.isPOSITIVE()) || (this.isNEGATIVE() && param.isNEGATIVE());
    }
    private int remainderOfSum(String a, String b) {
        int newA = Integer.parseInt(a);
        int newB = Integer.parseInt(b);

        if (newA + newB > 10)
            return 1;
        else
            return DEF_VAL;
    }
    private int sum(String a, String b) {
        int newA = Integer.parseInt(a);
        int newB = Integer.parseInt(b);

        if (newA + newB >= 10)
            return (newA + newB) % 10;
        else
            return newA + newB;
    }

    /**
     * the object that called the method is bigger size, and now
     * we need to push param
     */
    private ArrayList<String> makeSameSize(BigInt param, int size) {
        param.getBig().remove(DEF_VAL);
        for (int i = 0; i < size; i++) {
            param.getBig().add(i, "0");
        }
        if (param.isPOSITIVE()) {
            param.getBig().add(DEF_VAL, param.PositiveSign());
        } else
            param.getBig().add(DEF_VAL, param.NegativeSign());

        return param.getBig();
    }

    public BigInt plus(BigInt param) {

        int dif = Math.abs(this.getBig().size() - param.getBig().size());
        ArrayList<String> res = new ArrayList<>();

        /** if THIS number is BIGGER than PARAM number */
        if (this.compareTo(param) > DEF_VAL) {
            /** we make the PARAM size equal to THIS number, so we can add them */
            param.setList(this.makeSameSize(param, dif));

            /** if both numbers are have same sign*/
            if (isTheSameSign(param)) {
                for (int i = this.getBig().size() - 1; i > 0; i--) {
                    if (i == 1) {
                        if (remainderOfSum(getBig().get(i + 1), param.getBig().get(i + 1)) != DEF_VAL) {
                            res.add(DEF_VAL, String.valueOf(sum(getBig().get(i), param.getBig().get(i)) +
                                    remainderOfSum(getBig().get(i + 1), param.getBig().get(i + 1))));
                            res.add(DEF_VAL, String.valueOf(remainderOfSum(getBig().get(i + 1), param.getBig().get(i + 1))));
                            break;
                        } else {
                            res.add(DEF_VAL, String.valueOf(sum(getBig().get(i), param.getBig().get(i))));
                        }
                    }
                    if (i == this.getBig().size() - 1) {
                        res.add(DEF_VAL, String.valueOf(sum(getBig().get(i), param.getBig().get(i))));
                    }
                    if (i != this.getBig().size() - 1 && i != 1) {
                        if (remainderOfSum(getBig().get(i + 1), param.getBig().get(i + 1)) != DEF_VAL) {
                            res.add(DEF_VAL, String.valueOf(sum(getBig().get(i), param.getBig().get(i)) +
                                    remainderOfSum(getBig().get(i + 1), param.getBig().get(i + 1))));
                        } else {
                            res.add(DEF_VAL, String.valueOf(sum(getBig().get(i), param.getBig().get(i))));
                        }
                    }
                }
                res.add(DEF_VAL, getBig().get(DEF_VAL));
            } else {
                this.minus(param);
                if (this.isNEGATIVE() && param.isPOSITIVE()) {
                    res.remove(DEF_VAL);
                    res.add(DEF_VAL, "-");
                } else if (this.isPOSITIVE() && param.isNEGATIVE()) {
                    res.remove(DEF_VAL);
                    res.add(DEF_VAL, "+");
                }
            }
        }
        

        /** if the numbers are equal */
        else if(this.compareTo(param)==DEF_VAL){
            this.mul(new BigInt("2"));
       }

        /** if this object is SMALLER than param object */
        else{
            this.setList(param.makeSameSize(this,dif));
            if(isTheSameSign(param)){
                for(int i=param.getBig().size()-1;i>0;i--){
                //TODO THE SAME AS ABOVE INVERTED
                }

            }
            /** if both numbers have different sign then bigger minus smaller*/
            else{
                param.minus(this);
                if(param.isNEGATIVE() && this.isPOSITIVE()){
                    res.remove(DEF_VAL);
                    res.add(DEF_VAL,"-");
                }
                else if(param.isPOSITIVE() && this.isNEGATIVE()){
                    res.remove(DEF_VAL);
                    res.add(DEF_VAL,"+");
                }
            }
       }
        System.out.println(res);
        return new BigInt(toString(res));
    }
    public BigInt minus(BigInt param){ return this; }
    public BigInt mul(BigInt param){
        BigInt Bigger;
        BigInt Smaller;
        ArrayList<String> res = new ArrayList<>();
        ArrayList<String> all = new ArrayList<>();

        
        if(this.compareTo(param)>DEF_VAL || this.compareTo(param)==DEF_VAL ) {
            Bigger = this;
            Smaller = param;
        }
        else {
            Bigger = param;
            Smaller = this;
        }
        int BiggerSize = Bigger.getBig().size()-1;
        System.out.println("Bigger is: " + Bigger.getBig().toString());
        System.out.println("Bigger size: " + BiggerSize);
        int SmallerSize = Smaller.getBig().size()-1;
        System.out.println("Smaller is: " + Smaller.getBig().toString());
        System.out.println("Smaller size: " + SmallerSize);

        int remainder = 0;
        int SmallInI;
        int BigInJ;
        int counter = 0;

        for(int i=SmallerSize;i>0;i--){
            addZeros(res,counter);
            for(int j=BiggerSize;j>0;j--){
                System.out.println("------");
                BigInJ = Integer.parseInt(Bigger.getBig().get(j));
                SmallInI = Integer.parseInt(Smaller.getBig().get(i));
                if(j==BiggerSize) {
                    System.out.println("Entered IF ");
                    System.out.println("index i is: " + i);
                    System.out.println("index j is: " + j);
                    System.out.println("multi is: " + (multiDigits(Bigger.getBig().get(j), Smaller.getBig().get(i)) + remainder));
                    System.out.println("remainder is: " + remainder);

                    res.add(DEF_VAL,String.valueOf(multiDigits(Bigger.getBig().get(j), Smaller.getBig().get(i))));
                    remainder = remainderOfMulti(Bigger.getBig().get(j),Smaller.getBig().get(i));
                }
                else if(j==1){
                    System.out.println("Entered IF-ELSE ");
                    System.out.println("index i is: " + i);
                    System.out.println("index j is: " + j);
                    System.out.println("multi is: " + (multiDigits(Bigger.getBig().get(j), Smaller.getBig().get(i)) + remainder));
                    System.out.println("remainder is: " + remainder);

                    int a = ((BigInJ * SmallInI) + remainder)%10;
                    res.add(DEF_VAL,String.valueOf(a));
                    a = ((BigInJ * SmallInI) + remainder)/10;
                    if(a!=0)
                        res.add(DEF_VAL,String.valueOf(a));
                }
                else{
                    System.out.println("Entered ELSE ");
                    System.out.println("index i is: " + i);
                    System.out.println("index j is: " + j);
                    System.out.println("multi is: " + (multiDigits(Bigger.getBig().get(j), Smaller.getBig().get(i)) + remainder));
                    System.out.println("remainder is: " + remainder);
                    int a = ((BigInJ * SmallInI) + remainder)%10;
                    remainder = ((BigInJ * SmallInI) + remainder)/10;

                    res.add(DEF_VAL,String.valueOf(a));
                }
                System.out.println("Added successful");
                System.out.println(res);
                //System.out.println("All is: " + all);

            }
            res.add(DEF_VAL,"+");
            String str = Arrays.toString(res.toArray());

            all.add(str);
            System.out.println("All is: " + all);
            res.clear();
            counter++;

        }
        System.out.println("---------------------");
        //System.out.println("This is after the nested loops: " + all);




        for(int i=0;i<all.size();i++){
            Smaller.getBig().clear();
            Smaller.setList(all.get(i));
            System.out.println("b1 is: " + Smaller);
           // Bigger.setList(all.get(i+1));
            //System.out.println("b2 is: " + Bigger);
            //b3 = b1.plus(b2);
            //System.out.println("b3 is: " + Smaller);


        }



        return new BigInt(all.toString());
    }




    private void addZeros(ArrayList<String> a,int ind){
        for(int i=0;i<ind;i++)
            a.add("0");
    }
    private int multiDigits(String a,String b){
        int newA = Integer.parseInt(a);
        int newB = Integer.parseInt(b);
        int multi = newA*newB;
        return multi>10?multi%10:multi;
    }
    private int remainderOfMulti(String a,String b){
        int newA = Integer.parseInt(a);
        int newB = Integer.parseInt(b);
        int multi = newA*newB;
        return multi>10?multi/10:DEF_VAL;
    }


    public BigInt div(BigInt param){
        return this;
    }
    @Override
    public boolean equals(Object obj) {
        return getBig() == obj;
    }
    public String toString(ArrayList<String> arr){
        String result = "";
        for (String s : arr) {
            result = s;
        }
        return result;
    }
    @Override
    public int compareTo(BigInt param) {
        int SMALLER = -1;
        int EQUAL = 0;
        int BIGGER = 1;

        if(this.getBig().size()>param.getBig().size()){
            if(this.isPOSITIVE()){
                return BIGGER;
            }
            else{
                return SMALLER;
            }
        }
        else if(param.getBig().size()>this.getBig().size()){
            if(param.isPOSITIVE()){
                return SMALLER;
            }
            else{
                return BIGGER;
            }
        }
        else{
            if(!Objects.equals(this.getBig().get(DEF_VAL), param.getBig().get(DEF_VAL))){
                if(this.isPOSITIVE()){
                    return BIGGER;
                }
                else
                    return SMALLER;
            }
            else{
                int thisTemp;
                int paramTemp;
                for(int i=1;i<getBig().size();i++) {
                    thisTemp = Integer.parseInt(this.getBig().get(DEF_VAL + i));
                    paramTemp = Integer.parseInt(param.getBig().get(DEF_VAL + i));
                    if (thisTemp>paramTemp) {
                        return BIGGER;
                    }
                    else if(paramTemp>thisTemp)
                        return SMALLER;
                }
            }
        }
        return EQUAL;
    }
    public String toString(){
        StringBuilder res = new StringBuilder();
        for(int i = 0; i<getBig().size(); i++)
            res.append(getBig().get(i));
        return res.toString();
    }

    public static String ArrayListToString(ArrayList<String> arr){
        String res = "";
        for(int i=0;i<arr.size();i++)
            res+=arr.get(i);
        return res;
    }

//--------------------------------------------------------------------------------//
    public static void main(String[] args) {
        BigInt b1 = new BigInt("+123");
        BigInt b2 = new BigInt("+19");
        //System.out.println(b1.getBig());
        //System.out.println(b2.getBig());
        //System.out.println(b1.remainderOfMulti("3","9"));

        System.out.println(b1.mul(b2));

        //ArrayList<ArrayList<String>> all = new ArrayList<>();
/*
        ArrayList<String> res1 = new ArrayList<>();
        res1.add("+");
        res1.add("1");
        res1.add("2");
        res1.add("8");
        System.out.println(res1);
        String str = ArrayListToString(res1);
        System.out.println(str);

        BigInt b = new BigInt(str);
        System.out.println("b is: " + b.toString());

*/

    }
}
