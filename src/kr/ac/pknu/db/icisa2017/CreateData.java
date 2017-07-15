package kr.ac.pknu.db.icisa2017;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by jmkim on 2/26/17.
 */
public class CreateData {
    final static Epoch startEpoch = new Epoch("2006", "yyyy");
    final static Epoch endEpoch = new Epoch("01-01-2006 23:59:59.999 UTC");
    //final static Epoch endEpoch = new Epoch("31-12-2016 23:59:59.999 UTC");

    final static int minInterval = 5;
    final static int maxInterval = 1000;

    public static void main(String[] args) throws ParseException, FileNotFoundException, IOException {
        PrintWriter out = new PrintWriter("/home/jmkim/IdeaProjects/icisa2017/res/sampleData.csv");

        /*  DATA FORMAT
            TIME (epoch time)
            SENDER_ID
            TRANS_TYPE
            TRANS_VALUE
            RECEIVER_ID
            SENDER_BALANCE
            RECEIVER_BALANCE
         */

        {
            long time = startEpoch.getEpoch();
            Random random = new Random();

            while (time < endEpoch.getEpoch()) {
                StringBuilder output = new StringBuilder();

                String transType;
                int transValue = random.nextInt(10000000);
                int senderId;
                int receiverId;
                int senderBalance;
                int receiverBalance;

                switch (random.nextInt(2)) {
                    case TransType.DEPOSIT:
                        transType = TransType.String[TransType.DEPOSIT];
                        senderId = random.nextInt(100000);
                        receiverId = senderId;
                        senderBalance = random.nextInt(10000000);
                        receiverBalance = senderBalance + transValue;
                        break;

                    case TransType.TRANSFER:
                    default:
                        transType = TransType.String[TransType.TRANSFER];
                        senderId = random.nextInt(100000);
                        receiverId = random.nextInt(100000);
                        senderBalance = random.nextInt(10000000);
                        receiverBalance =
                                (senderId == receiverId)
                                        ? (senderBalance + transValue)
                                        : (random.nextInt(10000000) + transValue);
                        break;
                }

                output.append(time);
                output.append("\t");
                output.append(senderId);
                output.append("\t");
                output.append(transType);
                output.append("\t");
                output.append(transValue);
                output.append("\t");
                output.append(receiverId);
                output.append("\t");
                output.append(senderBalance);
                output.append("\t");
                output.append(receiverBalance);
                output.append("\n");

                out.print(output.toString());

                time += random.nextInt(maxInterval - minInterval) + minInterval;
            }
        }

        out.close();
    }

    private static final class TransType {
        static final int DEPOSIT = 0;
        static final int TRANSFER = 1;
        static final String String[] = {
                "Deposit",
                "Transfer"
        };
    }

    private static class Epoch {
        String format;
        String timeStr;

        Epoch(String timeStr) {
            this(timeStr, "dd-MM-yyyy HH:mm:ss.SSS zzz"); /* Default date format */
        }

        Epoch(String timeStr, String format) {
            this.timeStr = timeStr;
            this.format = format;
        }

        long getEpoch() throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return (dateFormat.parse(timeStr).getTime());
        }

        @Override
        public String toString() {
            return (timeStr);
        }
    }
}
