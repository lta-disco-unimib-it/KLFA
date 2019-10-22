package check.ioInvariantParser;

import sjm.parse.Assembler;
import sjm.parse.Assembly;

public class NullAssembler extends Assembler {

  public void workOn(Assembly a) {
    Target target = (Target)a.getTarget();
    target.push(null);
  }
}
