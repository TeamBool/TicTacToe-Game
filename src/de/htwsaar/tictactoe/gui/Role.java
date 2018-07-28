package de.htwsaar.tictactoe.gui;



   public class Role {
    private String roleID;
    private String roleName;

    public Role(String roleID, String roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

   
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }
    
    
} 

