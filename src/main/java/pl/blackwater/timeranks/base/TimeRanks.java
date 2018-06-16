package pl.blackwater.timeranks.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import pl.blackwaterapi.API;

@Getter
@Setter
public class TimeRanks 
{
	private String lastname;
    private String rank;
    private String previousRank;
    private Long expireTime;
    
    public TimeRanks(String lastname, String rank, String previousRank, Long expiretime)
    {
    	this.lastname = lastname;
    	this.rank = rank;
    	this.previousRank = previousRank;
    	this.expireTime = expiretime;
    	insert();
    }
    
    public TimeRanks(ResultSet rs)
    throws SQLException
    {
    	this.lastname = rs.getString("lastname");
    	this.rank = rs.getString("rank");
    	this.previousRank = rs.getString("previousRank");
    	this.expireTime = rs.getLong("expiretime");
    }
    
    private void insert()
    {
    	API.getStore().update(false,"INSERT INTO `{P}timeranks`(`id`,`lastname`,`rank`,`previousrank`,`expiretime`) VALUES (NULL, '" + getLastname() + "','" + getRank() + "','" + getPreviousRank() + "','" + getExpireTime() + "');");
    }
}
