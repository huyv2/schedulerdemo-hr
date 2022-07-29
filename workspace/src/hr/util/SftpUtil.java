package hr.util;

import java.util.Properties;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpUtil {
	public static void uploadSftp(String remoteHost, int remotePort, String username, String password, String sourcePathFile, String destinationPathFile) throws JSchException, SftpException {
	    ChannelSftp channelSftp = setupJsch(remoteHost, remotePort, username, password);
	    channelSftp.connect();
	 
	    channelSftp.put(sourcePathFile, destinationPathFile);
	 
	    channelSftp.exit();
	}
	
	public static void downloadSftp(String remoteHost, int remotePort, String username, String password, String sourcePathFile, String destinationPathFile) throws JSchException, SftpException {
	    ChannelSftp channelSftp = setupJsch(remoteHost, remotePort, username, password);
	    channelSftp.connect();
	 
	    channelSftp.get(sourcePathFile, destinationPathFile);
	 
	    channelSftp.exit();
	}
	
	private static ChannelSftp setupJsch(String remoteHost, int remotePort, String username, String password) throws JSchException {
	    JSch jsch = new JSch();
	    jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
	    Session jschSession = jsch.getSession(username, remoteHost, remotePort);
	    jschSession.setPassword(password);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		jschSession.setConfig(config);
	    jschSession.connect();
	    return (ChannelSftp) jschSession.openChannel("sftp");
	}
}
