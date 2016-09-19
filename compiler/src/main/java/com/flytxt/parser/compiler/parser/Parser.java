package com.flytxt.parser.compiler.parser;

public class Parser {

    protected String[] keywords = { "folder", "line", "split", "element", "currentFile" };

    private final LineParser lp = new LineParser();

    private final FolderParser fp = new FolderParser();

    private final StorageParser sp = new StorageParser();

    private String processorName = "";

    public void setName(final String n) {
        this.processorName = n;
    }

    public void checkSyntax(final String line) {
        final String[] res = line.replace("\\s+", "").split("=>");
    }

    public void process(final String sCurrentLine) {
        if (fp.check(sCurrentLine)) {
            fp.process(sCurrentLine);
        } else if (sp.check(sCurrentLine)) {
            sp.process(sCurrentLine);
        } else {
            lp.process(sCurrentLine);
        }
    }

    public void done() {
        fp.done();
        lp.done();
        sp.done();
        // logger.debug(createProcessClass());
    }

    public String createProcessClass() {
        return "package com.flytxt.utils.parser;\n" + "import java.util.List;\n" + "import com.flytxt.parser.store.*;\n" + "import com.flytxt.parser.marker.*;\n" + "import java.io.IOException;\n"
                + "public  class " + processorName + " implements LineProcessor{\n" + sp.getMembers() + "\n" + lp.getMemberVar() + fp.getInput() + fp.getFileFilter()
                + "public final  int getMaxListSize(){ return " + lp.getMaxVallue() + ";}\n" + "public final void done() throws IOException{" + sp.getDoneCode() + "}\n"
                + "public final void setInputFileName(String currentFileName){ this.currentFileName = currentFileName;}\n"
                + "public final void process(byte[] data, int lineSize, MarkerFactory mf) throws IOException{\n" + lp.getCode() + "\n" + sp.getCode() + "\n" + "}" + "}";

    }
}