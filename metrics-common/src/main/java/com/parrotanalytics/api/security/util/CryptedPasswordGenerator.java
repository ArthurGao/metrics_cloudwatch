
package com.parrotanalytics.api.security.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.lang3.RandomStringUtils;

import com.parrotanalytics.api.security.SecurityConfiguration;
import com.parrotanalytics.logging.ParrotLoggingController;

import ch.qos.logback.classic.Level;

/**
 * The Class CryptedPasswordGenerator generates a bcryped password.
 * 
 * @author Chris
 */
public class CryptedPasswordGenerator
{
    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(String[] args)
    {
        new ParrotLoggingController(Level.WARN);

        for (int i = 0; i < 10; i++)
        {
            String pw = RandomStringUtils.randomAlphanumeric(9);
            System.out.println(pw + " " + SecurityConfiguration.getPasswordEncoder().encode(pw));
        }

        // while (true)
        // {
        // System.out.println("\n\nEnter password to encrypt: ");
        // String encrypted =
        // SecurityConfiguration.getPasswordEncoder().encode(readLine(true));
        // System.out.println("\nEncrypted pw: " + encrypted);
        // }
        // System.out.println("\nPress enter to exit");
        // readLine(false);
    }

    private static String readLine(boolean repeatIfEmpty)
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        try
        {
            line = br.readLine();
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
        if (repeatIfEmpty && line == null)
        {
            System.out.println("Enter an input please...");
            return readLine(true);
        }
        return line;
    }
}
