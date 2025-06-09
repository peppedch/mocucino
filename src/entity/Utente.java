package entity;

import java.util.ArrayList;

public class Utente {
    //tutti a private.
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    private ProfiloPersonale profiloPersonale;
    private ArrayList<Raccolta> raccolteList;

    public Utente(String username, String nome, String cognome, String email, String password, Raccolta raccoltaDefault) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.raccolteList = new ArrayList<>();
        this.raccolteList.addFirst(raccoltaDefault);
    }

    public void aggiungiProfiloPersonale(ProfiloPersonale profiloPersonale) {
        this.profiloPersonale = profiloPersonale;
    }

    public void creaRaccolta(Raccolta raccolta) {
        this.raccolteList.add(raccolta);
    }

    //overload del metodo creaRicetta: se passi la raccolta lo aggiunge a questa raccolta
    public boolean creaRicetta(Ricetta ricetta, Raccolta raccolta){
        if(this.raccolteList.contains(raccolta)){
            raccolta.aggiungiRicettaARaccolta(ricetta);
            return true;
        }else{
            return false;
        }
    }

    //overload del metodo creaRicetta: se non passi la raccolta lo aggiungue alla prima che e quella di defaul
    public boolean creaRicetta(Ricetta ricetta){
        this.raccolteList.getFirst().aggiungiRicettaARaccolta(ricetta);
        return true;
    }


    //getter e setter
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProfiloPersonale getProfiloPersonale() {
        return this.profiloPersonale;
    }

    public void setProfiloPersonale(ProfiloPersonale profiloPersonale) {
        this.profiloPersonale = profiloPersonale;
    }

    public ArrayList<Raccolta> getRaccolteList() {
        return this.raccolteList;
    }

    public void setRaccolteList(ArrayList<Raccolta> raccolteList) {
        this.raccolteList = raccolteList;
    }









}
