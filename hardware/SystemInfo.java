package hardware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SystemInfo {

    // Método utilitário para executar comandos e retornar a saída como uma String
    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Erro ao executar comando: " + command;
        }
        return output.toString();
    }

    public static String getOSInfo() {
        String osName = System.getProperty("os.name");
        String osVersion = System.getProperty("os.version");
        String osArch = System.getProperty("os.arch");
        return "Sistema Operacional: " + osName + "<br>Versão: " + osVersion + "<br>Arquitetura: " + osArch;
    }

    public static String getProcessorInfo() {
        String processorArch = System.getProperty("sun.arch.data.model");
        return "Arquitetura do Processador: " + processorArch + " bits";
    }

    public static String getGPUInfo() {
        String output = executeCommand("wmic path Win32_VideoController get Caption,DriverVersion,AdapterRAM,AdapterCompatibility,VideoModeDescription");
        return "GPU: <br>" + output.replaceAll("\\b(Caption|DriverVersion|AdapterRAM|AdapterCompatibility|VideoModeDescription)\\b", "").trim().replace("\n", "<br>");
    }

    public static String getBIOSInfo() {
        String output = executeCommand("wmic bios get Manufacturer,SMBIOSBIOSVersion");
        return "BIOS Info: " + output.replaceAll("\\b(Manufacturer|SMBIOSBIOSVersion)\\b", "").trim().replace("\n", "<br>");
    }

    public static String getPeripheralsInfo() {
        String output = executeCommand("wmic path Win32_PnPEntity get Caption");
        String[] lines = output.split("\n");
        List<String> peripherals = new ArrayList<>();
        for (String line : lines) {
            if (!line.isEmpty() && isRelevantPeripheral(line.trim())) {
                peripherals.add(line.trim());
            }
        }
        return formatPeripherals(peripherals);
    }

    private static boolean isRelevantPeripheral(String line) {
        String lowerCaseLine = line.toLowerCase();
        return lowerCaseLine.contains("keyboard") || 
               lowerCaseLine.contains("mouse") || 
               lowerCaseLine.contains("headset") || 
               lowerCaseLine.contains("speaker") || 
               lowerCaseLine.contains("webcam") ||
               lowerCaseLine.contains("camera") ||
               lowerCaseLine.contains("microphone");
    }

    private static String formatPeripherals(List<String> peripherals) {
        StringBuilder formattedOutput = new StringBuilder("Periféricos Para Teste:<br>");
        for (String peripheral : peripherals) {
            formattedOutput.append(" - ").append(peripheral).append("<br>");
        }
        return formattedOutput.toString();
    }

    public static String getMemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory() / (1024 * 1024);
        long totalMemory = runtime.totalMemory() / (1024 * 1024);
        long maxMemory = runtime.maxMemory() / (1024 * 1024);
        return "Memória Livre: " + freeMemory + " MB<br>Memória Total: " + totalMemory + " MB<br>Memória Máxima: " + maxMemory + " MB";
    }
}
