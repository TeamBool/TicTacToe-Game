package de.htwsaar.tictactoe;

import org.apache.commons.cli.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class main {
    /**
     * This method is called when the {@link Server} is started.
     *
     * @param args Command line arguments
     */
    public static void main(final String[] args) {

        //Setup options
        Options options = setupOptions();

        // Setup Parser
        CommandLineParser parser = new DefaultParser();
        CommandLine line = null;

        // run parser
        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            exit("Error while parsing:\n" + e.getMessage());
        }

        // Handle parsed results
        assert line != null; // TODO maybe redundant
        // Server
        if (line.hasOption("server")) {

            // Check if args are valid
            if (!checkServerArgs(line)) {
                exit("Invalid server arguments");
            }
            // Get parameters
            int port = Integer.parseInt(line.getOptionValue("port"));
            setupServer(port);
        }
        // Client
        else if (line.hasOption("gui")) {
            //Check if args are valid
            if (!checkGuiArgs(line)) {
                exit("Invalid Gui arguments!");
            }
            // Parse gui arguments
            String host = line.getOptionValue("host");
            int port = Integer.parseInt(line.getOptionValue("port"));
            int timeout = Integer.parseInt(line.getOptionValue("timeout", "1000"));

            setupGui(host, port);
        }
        // Human player mode
        else if (line.hasOption("player")) {
            // Check if args are valid
            if (!checkPlayerArgs(line)) {
                exit("Invalid player arguments");
            }
            // Get parameters
            String host = line.getOptionValue("host");
            int port = Integer.parseInt(line.getOptionValue("port"));
            int timeout = Integer.parseInt(line.getOptionValue("timeout", "1000"));
            String name = line.hasOption("name") ? line.getOptionValue("name") : null;
            String team = line.hasOption("team") ? line.getOptionValue("team") : null;
            String monster = line.hasOption("monster") ? line.getOptionValue("monster").toUpperCase() : null;

            // Create and start a human player
            setupHumanPlayer(host, port);
        }
        else {
            exit("Invalid parameters! Use either -server, -ki, -test, -player or -gui");
        }
    }

    /**
     * Tests if commandline has needed options for server
     *
     * @param line {@link CommandLine} to controll
     * @return Returns true if arguments are valid
     */
    private static boolean checkServerArgs(CommandLine line) {
        return line.hasOption("port")
                && line.hasOption("map")
                && line.hasOption("file");
    }

    /**
     * Tests if commandline has needed options for client
     *
     * @param line {@link CommandLine} to control
     * @return Returns true if arguments are valid
     */

    private static boolean checkClientArgs(CommandLine line) {
        return line.hasOption("port")
                && line.hasOption("host");
    }

    /**
     * Tests if commandline has needed options for test
     *
     * @param line {@link CommandLine} to control
     * @return Returns true if arguments are valid
     */

    private static boolean checkTestArgs(CommandLine line) {
        return line.hasOption("json")
                && line.hasOption("port");
    }

    /**
     * Tests if commandline has needed options for gui
     *
     * @param line {@link CommandLine} to control
     * @return Returns true if arguments are valid
     */

    private static boolean checkGuiArgs(CommandLine line) {
        return line.hasOption("port")
                && line.hasOption("host");
    }

    /**
     * Tests if commandline has needed options for human player
     *
     * @param line {@link CommandLine} to control
     * @return Returns true if arguments are valid
     */
    private static boolean checkPlayerArgs(CommandLine line) {
        return line.hasOption("port")
                && line.hasOption("host");
    }

    /**
     * Setup and start of {@link Server}
     *
     * @param port      Port for {@link Server}
     */
    private static void setupServer(int port) {
        // Tries to build a server and handles upcoming exceptions via ending program
        try {
            Server server = new Server(port);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
            exit("Error reading files:\n" + e.getMessage());
        }
    }

    /**
     * Setup and start of a {@link KI} ({@link Client})
     *
     * @param host         Name of the host
     * @param port         Port for connection
     */
    private static void setupClient(String host, int port, int timeout, String name, String teamname, String creatureType) {
        /*
        Client ki = null;
        CreatureType ct = null;
        try {
            ct = CreatureType.valueOf(creatureType);
        } catch (IllegalArgumentException e) {
            exit("Monster type does not exist:\n" + e.getMessage());
        }
        assert ct != null; // Just to make sure, but it should not happen
        switch (ct) {
            case KOBOLD:
                ki = new KoboldKI(name, port, timeout, teamname, host);
                break;
            case ELF:
                ki = new ElfKI(name, port, timeout, teamname, host);
                break;
            case ROOK:
                ki = new RookKi(name, port, timeout, teamname, host);
                break;
            case IFRIT:
                ki = new IfritKi(name, port, timeout, teamname, host);
                break;
            case YETI:
                ki = new YetiKi(name, port, timeout, teamname, host);
                break;
            case WRAITH:
                ki = new WraithKi(name, port, timeout, teamname, host);
                break;
            case NAGA:
                ki = new NagaKi(name, port, timeout, teamname, host);
                break;
            default:
                exit("It is not possible to play a boar or a fairy!");
        }
        assert ki != null; // Just to make sure, but it should not happen
        ki.run();*/
        throw new UnsupportedOperationException();
    }


    /**
     * Setup and start of the testmode
     *
     * @param tests      Path to <tests>.jar
     * @param outputFile Target file for test output
     * @param port       Port for connection
     * @param timeout    Timeout for client-server connection
     * @param dport      Debugger port
     *
    private static void setupTests(String tests, String outputFile, int port, int timeout, int dport) {

        // Open files
        File tFile = new File(tests);
        File oFile = new File(outputFile);

        // Start test controller and get tests
        TestController tc = new TestController(tFile, port, dport, timeout, oFile);
        List<SystemTest> sysTests = setupSystemTests();

        // Execute system tests
        try {
            tc.executeTests(sysTests);
        } catch (IOException e) {
            exit("IO error: " + e.getMessage());
        }

        // Print results
        System.out.println("SUCCESS: " + tc.count(SystemTest.TestState.SUCCESS) + "/" + tc.getTestCount());
        System.out.println("FAIL: " + tc.count(SystemTest.TestState.FAIL) + "/" + tc.getTestCount());
        System.out.println("NOEXEC: " + tc.count(SystemTest.TestState.NOEXEC) + "/" + tc.getTestCount());
        System.out.println("SERVERFAIL: " + tc.count(SystemTest.TestState.SERVERFAIL) + "/" + tc.getTestCount());

    }
    */
    /**
     * Runs gui
     *
     * @param host    host
     * @param port    port
     */
    private static void setupGui(String host, int port,) {
        Gui gui = new Gui(host, port);
        gui.run();
    }

    /**
     * Runs game in player mode
     *
     * @param host     Name of the host
     * @param port     Port for connection
     */

    private static void setupHumanPlayer(String host, int port) {
        Player human = new Player(host, port);
        human.run();
    }

    /**
     * Builds all required options
     *
     * @return {@link Options} with all needed {@link Option}
     */

    private static Options setupOptions() {

        // Setup classes
        Options options = new Options();
        OptionGroup mode = new OptionGroup();

        // add options for modes
        mode.addOption(new Option("server", "starts server mode"));       // Server
        mode.addOption(new Option("gui", "starts gui mode"));             // gui
        mode.addOption(new Option("player", "starts human player mode")); // human player

        options.addOptionGroup(mode);

        // parameter with arguments
        // Host
        options.addOption(Option.builder("host")
                .hasArg()
                .argName("host")
                .desc("Adress of host")
                .build());

        // Port
        options.addOption(Option.builder("port")
                .hasArg()
                .argName("port")
                .type(Number.class)
                .desc("port to listen")
                .build());

        // Output for tests (json)
        options.addOption(Option.builder("json")
                .hasArg()
                .argName("debuggerPort")
                .desc("Path to output file for test results")
                .build());

        // Debbuger port
        options.addOption(Option.builder("dport")
                .longOpt("debuggerport")
                .hasArg()
                .argName("port")
                .type(Number.class)
                .desc("port for debugging")
                .build());

        return options;
    }


    // utility methods

    /**
     * Pareses fightFile and returns the according String
     *
     * @param path Path to fightFile
     * @return returns the parsed fightFile String
     */

    private static String readFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Shutdowns program
     *
     * @param s Reason of shtudown
     */
    private static void exit(String s) {
        System.out.println(s);
        System.exit(1);
    }
}