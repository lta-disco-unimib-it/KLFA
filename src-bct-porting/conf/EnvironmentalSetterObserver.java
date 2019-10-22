package conf;

/**
 * This is the interface for the objects that want to be notified when a change in the EnvironmentalSetter occurrs
 *  
 * @author Fabrizio Pastore - fabrizio.pastore@disco.unimib.it
 *
 */
public interface EnvironmentalSetterObserver {
	public void update( EnvironmentalSetterEvent e );
}
