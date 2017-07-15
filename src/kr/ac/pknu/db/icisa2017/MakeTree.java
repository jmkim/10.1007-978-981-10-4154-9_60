package kr.ac.pknu.db.icisa2017;

import org.mapdb.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jmkim on 2/27/17.
 */
class ElapsedTime {
    private static long startTime;
    private static long endTime;

    ElapsedTime() {
        startTime = 0;
        endTime = 0;
    }

    private long getTime() {
        return (System.currentTimeMillis());
    }

    public void start() {
        startTime = getTime();
    }

    public void end() {
        endTime = getTime();
    }

    public long result() {
        if (startTime > endTime) {
            return (-1L);
        } else {
            return (endTime - startTime);
        }
    }

    @Override
    public String toString() {
        if (result() < 0) {
            return ("Error while getting elapsed time.");
        } else {
            return (result() + " milliseconds");
        }
    }
}

public class MakeTree {
    public static void main(String[] args) throws IOException {
        final String outputLocation[] = {
                "/home/jmkim/IdeaProjects/icisa2017/res/output/40g_1.csv",
                "/home/jmkim/IdeaProjects/icisa2017/res/output/40g_10.csv",
                "/home/jmkim/IdeaProjects/icisa2017/res/output/40g_20.csv",
                "/home/jmkim/IdeaProjects/icisa2017/res/output/40g_30.csv",
                "/home/jmkim/IdeaProjects/icisa2017/res/output/40g_40.csv",
        };
        final String inputLocation[] = {
                "/home/jmkim/IdeaProjects/icisa2017/res/input/40g_1.csv",
                "/home/jmkim/IdeaProjects/icisa2017/res/input/40g_10.csv",
                "/home/jmkim/IdeaProjects/icisa2017/res/input/40g_20.csv",
                "/home/jmkim/IdeaProjects/icisa2017/res/input/40g_30.csv",
                "/home/jmkim/IdeaProjects/icisa2017/res/input/40g_40.csv"
        };

        final String dbName[] = {
                "40g_1",
                "40g_10",
                "40g_20",
                "40g_30",
                "40g_40"
        };

        for (int i = 4; i <= 4; ++i) {
            MakeTree tree1 = new MakeTree(outputLocation[i], inputLocation[i], dbName[i]);

            ElapsedTime time = new ElapsedTime();

            time.start();
            tree1.Load(); /* Build BTree */
            time.end();

            System.out.println("Elapsed time (" + dbName[i] + "): " + time.toString());

            /*
            tree1.Use(new DBOperation() {
                @Override
                public void transaction(ConcurrentNavigableMap<Long, String> map) {
                    // B-Tree operation at here

                    System.out.println(map.get(1136041204035L));
                    System.out.println(map.get(1483228794335L));
                    //System.out.println(map.size());
                }
            });
            */
        }
    }

    private String outputLocation;
    private String inputLocation;
    private String dbName;
    private final static Pattern regex = Pattern.compile("^([0-9]+)\t([0-9a-zA-Z\t]*)");

    MakeTree(String outputLocation, String inputLocation, String dbName) {
        this.outputLocation = outputLocation;
        this.inputLocation = inputLocation;
        this.dbName = dbName;
    }

    /* Build a B-tree from inputLocation */
    public void Load() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(inputLocation)).useDelimiter(System.lineSeparator());
        DB db = DBMaker.fileDB(outputLocation).make();

        DB.TreeMapSink<Long, String> sink = db
                .treeMap(dbName, Serializer.LONG, Serializer.STRING)
                .createFromSink();

        while (scanner.hasNext()) {
            Matcher matcher = regex.matcher(scanner.next());

            while (matcher.find()) {
                //System.out.println(matcher.group(1));
                //System.out.println(matcher.group(2));
                sink.put(Long.valueOf(matcher.group(1)), matcher.group(2));
            }
        }

        sink.create();

        db.commit();
        db.close();
    }

    /* Callback function for Use */
    public interface DBOperation {
        void transaction(ConcurrentNavigableMap<Long, String> map);

    }

    /* B-Tree operation */
    public void Use(DBOperation dbOperation) {
        DB db = DBMaker.fileDB(outputLocation).make();

        BTreeMap<Long, String> map = db
                .treeMap(dbName)
                .keySerializer(Serializer.LONG)
                .valueSerializer(Serializer.STRING)
                .createOrOpen();

        dbOperation.transaction(map);

        db.commit();
        db.close();
    }
}
