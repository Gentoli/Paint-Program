package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;

public interface ITool{

    public Drawable deselect();

    public void selected();

    public Drawable handlePointerUpdate(PointerEvent e);

    public boolean handleModifierUpdated(ModifierEvent e);




}
