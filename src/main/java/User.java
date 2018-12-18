public class User {
    private int id;
    private String nome;
    private boolean update;
    private boolean delete;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", update=" + update +
                ", delete=" + delete +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public User(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public User() {
    }

    public User(int id, String nome, boolean update, boolean delete) {
        this.id = id;
        this.nome = nome;
        this.update = update;
        this.delete = delete;
    }
}
