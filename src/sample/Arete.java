package sample;

public class Arete {
    String source;
    String destination;
    int poids;

    public Arete() {
        this.source = "";
        this.destination = "";
        this.poids = 0;
    }

    public Arete(String source, String destination, int poids) {
        this.source = source;
        this.destination = destination;
        this.poids = poids;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }
}