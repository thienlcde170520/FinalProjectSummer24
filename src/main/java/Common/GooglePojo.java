/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author ASUS
 */
public class GooglePojo {
        private String id;
	private String email;
	private boolean verified_email;
	private String name;
	private String given_name;
	private String family_name;
	private String link;
	private String picture;
        private String password; // bonus
        private int Money;
        private String AvatarLink;
        private String RegistrationDate;
        
        // Set để lưu trữ các id đã sinh ra
        private  static Set<String> generatedIds = new HashSet<>();
        private static Set<String> generatedNames = new HashSet<>();
        
        public GooglePojo() {
        this.id = generateRandomId();
        this.name = generateRandomName();
        
        this.Money = 0;
        this.AvatarLink = "https://i.pinimg.com/736x/bc/43/98/bc439871417621836a0eeea768d60944.jpg";
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.RegistrationDate = now.format(formatter);
        // Generate a random password when creating a new GooglePojo object
        this.generateRandomPassword();
        }
        
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
            if (id == null || id.isEmpty()) {
                this.id = generateRandomId();
            } else {
                this.id = id;
            }
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isVerified_email() {
		return this.verified_email;
	}

	public void setVerified_email(boolean verified_email) {
		this.verified_email = verified_email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
        if (name == null) {
            this.name = generateRandomName();
        } else {
            this.name = name;
        }
    }

	public String getGiven_name() {
		return this.given_name;
	}

	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}

	public String getFamily_name() {
		return this.family_name;
	}

	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
        
        public String getPassword() {
        return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getMoney() {
            return Money;
        }

        public void setMoney(int Money) {
            this.Money = Money;
        }

        public String getAvatarLink() {
            return AvatarLink;
        }

        public void setAvatarLink(String AvatarLink) {
            this.AvatarLink = AvatarLink;
        }

        public String getRegistrationDate() {
            return RegistrationDate;
        }

        public void setRegistrationDate(String RegistrationDate) {
            this.RegistrationDate = RegistrationDate;
        }
        // Hàm sinh ID ngẫu nhiên
        // Hàm sinh ID ngẫu nhiên và đảm bảo ID là duy nhất
        private String generateRandomId() {
            String characters = "0123456789";
            Random random = new Random();
            int length = 10; // Độ dài của số ngẫu nhiên
            String generatedId;
            do {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    int index = random.nextInt(characters.length());
                    sb.append(characters.charAt(index));
                }
                generatedId = "gamer_" + sb.toString();
            } while (generatedIds.contains(generatedId)); // Kiểm tra xem ID đã tồn tại chưa
            generatedIds.add(generatedId); // Đánh dấu ID đã sử dụng
            return generatedId;
        }
        
        
        // Method to generate a unique name
    private String generateRandomName() {
        Random random = new Random();
        String generatedName = null;
        do {
            int randomNumber = random.nextInt(1000000); // Generate a random number
            generatedName = "User_" + randomNumber;
        } while (generatedNames.contains(generatedName)); // Check if name already exists
        generatedNames.add(generatedName); // Mark the name as used
        return generatedName;
    }
    
        
        private void generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 10; // Set the length of the password
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        // Set the generated password
        this.setPassword(sb.toString());
    }
        
	public String toString() {
		return

		"GooglePojo [id=" + this.id + ", email=" + this.email + ", verified_email=" + this.verified_email + ", name="
				+ this.name + ", given_name=" + this.given_name + ", family_name=" + this.family_name + "]";
	}

    
}
