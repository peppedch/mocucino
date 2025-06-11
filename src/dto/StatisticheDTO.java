package dto;

public class StatisticheDTO {
    private int totalLikes;
    private int totalComments;
    private String mostLikedRecipe;

    public StatisticheDTO(int totalLikes, int totalComments, String mostLikedRecipe) {
        this.totalLikes = totalLikes;
        this.totalComments = totalComments;
        this.mostLikedRecipe = mostLikedRecipe;
    }

    // Getters
    public int getTotalLikes() {
        return totalLikes;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public String getMostLikedRecipe() {
        return mostLikedRecipe;
    }

    // Setters
    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }

    public void setMostLikedRecipe(String mostLikedRecipe) {
        this.mostLikedRecipe = mostLikedRecipe;
    }
} 