import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BareBonesInterpreter {
  public static void main(String[] args) throws IOException {
    BareBonesInterpreter interpreter = new BareBonesInterpreter();
    List<String> code = interpreter.getCode("C://Users/micha/IdeaProjects/BareBones/src/code.txt");
    System.out.println(code);
    HashMap<String, Integer> vars = new HashMap<>();
    int x = interpreter.interpret(code, vars);

  }

  public ArrayList<String> getCode(String path) throws IOException {
    //Load the file and create a reader object
    File file = new File(path);
    BufferedReader reader = new BufferedReader(new FileReader(file));

    //Load this code file into an array
    String st;
    ArrayList<String> code = new ArrayList<>();
    //Do so line by line
    while ((st = reader.readLine()) != null) {
      //Check if the line has been correctly completed
      if (st.endsWith(";")) {
        code.add(st.replace(";", ""));
      } else {
        System.out.println("Error: incomplete lines");
      }
    }
    return code;
  }
  public int interpret(List<String> code, HashMap vars) {

    List<String> executables = Arrays.asList("incr","decr","clear");

    for (int i=0; i<code.size(); i++) {
      String line = code.get(i);

      String[] splitLine = line.split(" ");
//      String[] splitLine = line.split("/\\s* \\s*/");

      if (line.startsWith("while")) {
        ArrayList<String> loop = new ArrayList<String>();

        loop.add(line);
        int j = i+1;
        while(!code.get(j).startsWith("end")) {
          loop.add(code.get(j).substring(3,code.get(j).length()));
          j++;
        }
        i=j;

        System.out.println("LOOP FOUND");
        System.out.println(loop);
        System.out.println("");

        //decipher while logic
        System.out.println(Arrays.toString(splitLine));
        while(!vars.get(splitLine[1]).equals(Integer.parseInt(splitLine[3]))) {
          this.interpret(loop.subList(1, loop.size()), vars);
        }
      } else if (executables.contains(splitLine[0])) {
        System.out.println(Arrays.toString(splitLine));

        vars = execute(splitLine[0], splitLine[1], vars);
      }
      }
    return 0;
  }
  public HashMap execute(String instr, String var, HashMap vars) {
    switch (instr) {
      case "clear":
        vars.put(var, 0);
        break;
      case "incr":
        Integer addOne = (Integer) vars.get(var) + 1;
        vars.put(var, addOne);
        break;
      case "decr":
        Integer takeOne = (Integer) vars.get(var) - 1;
        vars.put(var, takeOne);
      default:
    }
    System.out.println("W"+ vars.get("W"));
    System.out.println("X"+ vars.get("X"));
    System.out.println("Y"+ vars.get("Y"));
    System.out.println("Z"+ vars.get("Z"));
    return vars;
  }
  }