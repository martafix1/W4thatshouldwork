/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package w4_input.graphics_prototype;

/**
 *
 * @author martin
 */
public abstract class Enemy extends Entity{
    private int range;
    private short status;
    private int attackPeriod;
    public static final short IDLE = 0;
    public static final short TRIGGERED = 0;
    public static final short HUNTING = 0;
    
}
