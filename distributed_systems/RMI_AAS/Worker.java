
public class Worker{
    public int id;
    public Boolean busy = false;
    public String ip;
    public int port;

    public Worker(int id, String ip, int port){
	this.id = id;
	this.ip = ip;
	this.port = port;
    }

    @Override
    public String toString() {
	StringBuilder result = new StringBuilder();
	String NEW_LINE = System.getProperty("line.separator");
	result.append("Worker # "+this.id+NEW_LINE);
	result.append(" - IP:   "+this.ip+NEW_LINE);
	result.append(" - Port: "+this.port+NEW_LINE);

	return result.toString();
    }
}
