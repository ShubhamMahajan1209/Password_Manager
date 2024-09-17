package labprograms;

import java.util.ArrayList;
import java.util.Scanner;

// Represents a password entry with a name and corresponding password
class PassGet {     //?? Could be renamed Pass or PassEntry
    // Instance variables
    private String name;
    private String usname;
    private String getpass;

    // Constructor to initialize the password entry
    public PassGet(String name,String usname, String getpass) {
        this.name = name;
        this.usname=usname;
        this.getpass = getpass;
    }

    // Getter methods for retrieving the name and password
    public String getName() {
        return name;
    }

    public String getUsname() {
        return usname;
    }

    public String getGetpass() {
        return getpass;
    }

    // Setter methods (if needed) to update the name and password
    public void setName(String name) {
        this.name = name;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    public void setGetpass(String getpass) {
        this.getpass = getpass;
    }

    // toString method for easy printing of the password entry
    @Override
    public String toString() {
        return name +"+"+usname+ " = " + getpass;
    }
}

// Manages a collection of password entries
class PassManager {
    private ArrayList<PassGet> passwords;

    // Constructor to initialize the password manager with an empty list of passwords
    public PassManager() {
        this.passwords = new ArrayList<PassGet>();     //??
    }

    // Adds a password entry to the manager's list
    public void addPassGet(PassGet passes) {
        passwords.add(passes);
    }

    // Removes a password entry from the manager's list
    public void removePassGet(PassGet passes) {
        passwords.remove(passes);
    }

    // Retrieves the list of all password entries
    public ArrayList<PassGet> getPassGets() {
        return passwords;
    }
}

// Represents a set of character pools for password generation
class GNPool extends PassChecker {
    GNPool() {
    }

    // Character sets for uppercase letters, lowercase letters, numbers, and symbols
    String UppercaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String LowercaseLetters = "abcdefghijklmnopqrstuvwxyz";
    String Numbers = "1234567890";
    String Symbols = "!@#$%^&*()-_=+\\/~?";
    String Pool = "";
    int PoolSize;

    // Constructor to build the character pool based on user preferences
    public GNPool(boolean Upper, boolean Lower, boolean Num, boolean Sym) {
        if (Upper) {
            this.Pool = this.Pool + this.UppercaseLetters;
        }

        if (Lower) {
            this.Pool = this.Pool + this.LowercaseLetters;
        }

        if (Num) {
            this.Pool = this.Pool + this.Numbers;
        }

        if (Sym) {
            this.Pool = this.Pool + this.Symbols;
        }

        this.PoolSize = this.Pool.length();
    }
}

// Represents a password and calculates its strength
class PassChecker {
    String Value;
    int Length;
    double Score;

    // Default constructor
    public PassChecker() {
    }

    // Constructor to set the password value and calculate its length
    public PassChecker(String s) {
        this.Value = s;
        this.Length = s.length();
    }

    // Determines the type of character (uppercase, lowercase, number, symbol)
    public int CharType(char C) {
        byte val;
        if (C >= 'A' && C <= 'Z') {
            val = 1;
        } else if (C >= 'a' && C <= 'z') {
            val = 2;
        } else if (C >= '0' && C <= '9') {
            val = 3;
        } else {
            val = 4;
        }
        return val;
    }

    // Calculates the strength of the password based on character types and length
    public int PasswordStrength() {
        String s = this.Value;
        boolean UsedUpper = false;
        boolean UsedLower = false;
        boolean UsedNum = false;
        boolean UsedSym = false;
        int Score = 0;

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            int type = CharType(c);
            if (type == 1) {
                UsedUpper = true;
                Score = Score + 2;
            }

            if (type == 2) {
                UsedLower = true;
                ++Score;
            }

            if (type == 3) {
                UsedNum = true;
                Score = Score + 3;
            }

            if (type == 4) {
                UsedSym = true;
                Score = Score + 5;
            }
        }

        if (s.length() >= 8) {
            ++Score;
        }

        if (s.length() >= 16) {
            ++Score;
        }

        return Score;
    }

    // Provides a textual representation of the password strength
    public String CalculateScore() {
        int score = this.PasswordStrength();
        if (score >= 30) {
            return "This is a very good password :D check the Useful Information section to make sure it satisfies the guidelines";
        } else if (score >= 20) {
            return "This is a good password :) but you can still do better";
        } else {
            return score >= 10 ? "This is a medium password :/ try making it better" : "This is a weak password :( definitely find a new one";
        }
    }
}

// Handles password generation and user interactions
class Operations extends GNPool {
    GNPool GNPool;

    // Constructor to initialize the character pool based on user preferences
    public Operations(boolean IncludeUpper, boolean IncludeLower, boolean IncludeNum, boolean IncludeSym) {
        this.GNPool = new GNPool(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
    }

    // Default constructor
    public Operations() {
    }

    // Checks the strength of a manually entered password
    public void PasswordCheck() {
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter your password:");
        String Input = in.nextLine();
        PassChecker P = new PassChecker(Input);
        System.out.println(P.CalculateScore());
    }

    // Handles the process of generating a password based on user preferences
    public void PasswordRequest() {
        boolean IncludeUpper = false;
        boolean IncludeLower = false;
        boolean IncludeNum = false;
        boolean IncludeSym = false;
        Scanner in = new Scanner(System.in);
        System.out.println();
        System.out.println("Hello, welcome to the Password Generator :) answer the following questions by Yes or No \n");

        // Prompt for lowercase letters
        System.out.println("Do you want Lowercase letters \"abcd...\" to be used? ");
        String Input = in.nextLine();
        if (!Input.equalsIgnoreCase("YES")) {
            if (!Input.equalsIgnoreCase("NO")) {
                PasswordRequestError();
                return;
            }
        } else {
            IncludeLower = true;
        }

        // Prompt for uppercase letters
        System.out.println("Do you want Uppercase letters \"ABCD...\" to be used? ");
        Input = in.nextLine();
        if (!Input.equalsIgnoreCase("YES")) {
            if (!Input.equalsIgnoreCase("NO")) {
                PasswordRequestError();
                return;
            }
        } else {
            IncludeUpper = true;
        }

        // Prompt for numbers
        System.out.println("Do you want Numbers \"1234...\" to be used? ");
        Input = in.nextLine();
        if (!Input.equalsIgnoreCase("YES")) {
            if (!Input.equalsIgnoreCase("NO")) {
                PasswordRequestError();
                return;
            }
        } else {
            IncludeNum = true;
        }

        // Prompt for symbols
        System.out.println("Do you want Symbols \"!@#$...\" to be used? ");
        Input = in.nextLine();
        if (!Input.equalsIgnoreCase("YES")) {
            if (!Input.equalsIgnoreCase("NO")) {
                PasswordRequestError();
                return;
            }
        } else {
            IncludeSym = true;
        }

        // Validate that at least one character type is selected
        if (!IncludeUpper && !IncludeLower && !IncludeNum && !IncludeSym) {
            System.out.println("You have selected no characters to generate your password; at least one of your answers should be Yes");
        } else {
            // Generate the password
            System.out.println("Great! Now enter the length of the password");
            int length = in.nextInt();
            Operations G = new Operations(IncludeUpper, IncludeLower, IncludeNum, IncludeSym);
            String UserPass = G.GeneratePassword(length);
            System.out.println(UserPass);
        }
    }

    // Display an error message for incorrect input during password request
    public static void PasswordRequestError() {
        System.out.println("You have entered something incorrect; let's go over it again \n");
    }

    // Generates a password of specified length based on the character pool
    public String GeneratePassword(int Length) {
        String Pass = "";
        int n = this.GNPool.PoolSize;
        int max = n;
        int min = 0;
        int range = max - min + 1;

        for (int i = 0; i < Length; ++i) {
            int index = (int) (Math.random() * (double) range) + min;
            char Char = this.GNPool.Pool.charAt(index);
            Pass = Pass + Char;
        }

        return Pass;
    }
}

// Main class to run the program
public class Project {

    // Display the menu options
    public static void Menu() {
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("Enter 1 - Generate Password");
        System.out.println("Enter 2 - Check Password Strength ");
        System.out.println("Enter 3 - Save a Password");
        System.out.println("Enter 4 - Display a Password");
        System.out.println("Enter 5 - Password Info");
        System.out.println("Enter 6 - Exit");
        System.out.println("--------------------------------------------");
    }

    // Display useful information about password creation
    public static void UsefulInfo() {
        System.out.println();
        System.out.println("---------------------------------------------------------------------");
        System.out.println("Use a minimum password length of 8 or more characters if permitted");
        System.out.println("Include lowercase and uppercase alphabetic characters, numbers and symbols if permitted");
        System.out.println("Generate passwords randomly where feasible");
        System.out.println("Avoid using the same password twice (e.g., across multiple user accounts and/or software systems)");
        System.out.println("Avoid character repetition, keyboard patterns, dictionary words, letter or number sequences,\nusernames, relative or pet names, romantic links (current or past) and biographical information (e.g., ID numbers, ancestors' names or dates).");
        System.out.println("Avoid using information that the user's colleagues and/or acquaintances might know to be associated with the user");
        System.out.println("Do not use passwords which consist wholly of any simple combination of the aforementioned weak components");
        System.out.println("---------------------------------------------------------------------");
    }

    // Main method to run the program
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to SAM Password Services :)");
        // Creating a PassManager
        PassManager passesManager = new PassManager();

        // Accessing and printing passes details
        ArrayList<PassGet> allPassGets = passesManager.getPassGets();

        // Main program loop
        while (true) {
            // Display the main menu options
            Menu();

            // Prompt the user for their choice
            System.out.print("Enter your choice: ");
            int ch = in.nextInt();

            // Create an instance of the Operations class
            Operations O = new Operations();

            // Switch statement to handle user's choice
            switch (ch) {
                // Case 1: Generate Password
                case 1:
                    O.PasswordRequest();
                    break;

                // Case 2: Check Password Strength
                case 2:
                    O.PasswordCheck();
                    break;

                // Case 3: Save a Password
                case 3:
                    System.out.println("Enter the website name");
                    String nname = in.next();
                    System.out.println("Enter the username");
                    String uname = in.next();
                    System.out.println("Enter password");
                    String npass = in.next();
                    passesManager.addPassGet(new PassGet(nname,uname, npass));
                    break;

                // Case 4: Display Passwords (protected by a safety password)
                case 4:
                    System.out.print("Enter the safety password (numeric) to access all passwords: ");
                    int a = in.nextInt();
                    if (a == 1209) {
                        // Display all passwords
                        for (PassGet passes : allPassGets) {
                            System.out.println("Website: " + passes.getName());
                            System.out.println("UserName: " + passes.getUsname());
                            System.out.println("Password: " + passes.getGetpass());
                            System.out.println();
                        }
                    } else {
                        System.out.println("Try again :(\nThis is your last chance to access all passwords, or you will lose all your data");
                        System.out.print("Enter the safety password (numeric) to access all passwords: ");
                        int b = in.nextInt();
                        if (b == 1209) {
                            // Display all passwords
                            for (PassGet passes : allPassGets) {
                                System.out.println("Website: " + passes.getName());
                                System.out.println("UserName: " + passes.getUsname());
                                System.out.println("Password: " + passes.getGetpass());
                                System.out.println();
                            }
                        } else {
                            // Exit the program if safety password is incorrect
                            System.exit(0);
                        }
                    }
                    break;

                // Case 5: Display Useful Information
                case 5:
                    UsefulInfo();
                    break;

                // Case 6: Exit the program
                case 6:
                    System.out.println("Thank you for using SAM Password Services!!");
                    System.exit(0);

                    // Default case: Handle incorrect user choice
                default:
                    System.out.println("You have entered the wrong choice!");
                    System.out.println("Please try again.");


            }
        }
    }
}

