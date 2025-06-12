package dto;

public class ProfiloUtenteDTO {
    private String username;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String biografia;
    private String immagine;

    public ProfiloUtenteDTO(String username, String nome, String cognome, String email, String password, String biografia, String immagine) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.biografia = biografia;
        this.immagine = immagine;
    }

    // Getters
    public String getUsername() { return username; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getBiografia() { return biografia; }
    public String getImmagine() { return immagine; }

    // Setters
    public void setNome(String nome) { this.nome = nome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
    public void setImmagine(String immagine) { this.immagine = immagine; }
} 