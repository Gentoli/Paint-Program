package ca.utoronto.utm.paint;

import ca.utoronto.utm.pointer.ModifierEvent;
import ca.utoronto.utm.pointer.PointerEvent;

public interface ITool{

    public Drawable deselect();

    public Drawable handlePointerUpdate(PointerEvent e);

    public void handleModifierUpdated(ModifierEvent e);




}
