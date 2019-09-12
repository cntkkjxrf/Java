import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
import java.util.Objects;

public class Main {
    public static void help() {
        System.out.println("Enter the number of operation you want to be performed:");
        System.out.println("0 - exit");
        System.out.println("1 - create new task");
        System.out.println("2 - show all tasks");
        System.out.println("3 - show all not done tasks");
        System.out.println("4 - show all done tasks");
        System.out.println("5 - show details of some task");
        System.out.println("6 - mark some task done");
    }

    public static void showMenu(HashMap<Integer, ToDo> Data, Integer id) {
        int a = 1;
        Scanner in = new Scanner(System.in);
        help();
        while (a > 0) {
            System.out.println("If you need help enter 7");
            a = in.nextInt();
            switch (a) {
                case 0:
                    break;
                case 1:
                    createTask(Data, id, in);
                    System.out.println();
                    break;
                case 2:
                    showAllTasks(Data);
                    System.out.println();
                    break;
                case 3:
                    showNotDoneTasks(Data);
                    System.out.println();
                    break;
                case 4:
                    showDoneTasks(Data);
                    System.out.println();
                    break;
                case 5:
                    showDetails(Data, in);
                    System.out.println();
                    break;
                case 6:
                    markDone(Data, in);
                    System.out.println();
                    break;
                case 7:
                    help();
                    break;
                default:
                    System.out.println("    Wrong number!!!");
                    System.out.println();
            }
        }
        in.close();
    }

    public static void createTask(HashMap<Integer, ToDo> Data, Integer id, Scanner in) {
        in.nextLine();
        System.out.print("Name: ");
        String name = in.nextLine();
        System.out.print("Description: ");
        String descr = in.nextLine();
        System.out.print("If done write 'true': ");
        String done = in.nextLine();
        boolean flag;
        if (Objects.equals(done, "true"))
            flag = true;
        else
            flag = false;
        ToDo nTask = new ToDo(name, descr, flag);
        Data.put(id, nTask);

        System.out.println("    success");
    }

    public static void showAllTasks(HashMap<Integer, ToDo> Data) {
        for (Integer id : Data.keySet()) {
            System.out.println("ID = " + id);
            Data.get(id).showName();
        }
    }

    public static void showNotDoneTasks(HashMap<Integer, ToDo> Data) {
        for (Integer id : Data.keySet())
            if (!Data.get(id).done) {
                System.out.println("ID = " + id);
                Data.get(id).showName();
            }
    }

    public static void showDoneTasks(HashMap<Integer, ToDo> Data) {
        for (Integer id : Data.keySet())
            if (Data.get(id).done) {
                System.out.println("ID = " + id);
                Data.get(id).showName();
            }
    }

    public static void showDetails(HashMap<Integer, ToDo> Data, Scanner in) {
        System.out.println("Enter ID of task: ");
        Integer id = in.nextInt();
        if (!Data.containsKey(id)) {
            System.out.println("    Wrong ID!");
            return;
        }
        Data.get(id).show();
    }

    public static void markDone(HashMap<Integer, ToDo> Data, Scanner in) {
        System.out.println("Enter ID of task: ");
        Integer id = in.nextInt();
        if (!Data.containsKey(id)) {
            System.out.println("    Wrong ID!");
            return;
        }
        Data.get(id).makeDone();
    }

    public static void writeToFile(File f, HashMap<Integer, ToDo> Data) {
        if (f.exists()) {
            try {
                f.delete();
                f.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        // сама перезапись
        try {
            FileWriter writer = new FileWriter(f, false);
            for (ToDo task : Data.values()) {
                writer.write(task.name + '\n');
                writer.write(task.description + '\n');
                if (task.done) {
                    writer.write("true\n");
                } else {
                    writer.write("false\n");
                }
            }
            writer.flush();
            writer.close();
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        HashMap<Integer, ToDo> Data = new HashMap<Integer, ToDo>();
        Integer id = 0;
        File f = new File("todo_base.txt");
        if (f.exists()) { // считаем все таски из файла
            try {
                FileReader fr = new FileReader(f);
                BufferedReader reader = new BufferedReader(fr);
                String name = reader.readLine();
                String descr;
                String done;
                boolean flag;
                while (name != null) {
                    descr = reader.readLine();
                    done = reader.readLine();
                    if (Objects.equals(done, "true"))
                        flag = true;
                    else
                        flag = false;
                    ToDo nTask = new ToDo(name, descr, flag);
                    Data.put(id, nTask);
                    ++id;

                    name = reader.readLine();
                }
                fr.close();
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else { // таски считаны если есть
            try {
                f.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        showMenu(Data, id);
        writeToFile(f, Data);
        System.out.println("    Bye!");
    }
}
