package util.legacy;

import net.kyori.adventure.text.Component;

import java.util.function.UnaryOperator;

public class LegacyColorProcessor implements UnaryOperator<Component> {

    @Override
    public Component apply(Component component) {
        return component.replaceText(builder -> builder.match(".*")
                .replacement((result, replacementBuilder) -> Legacy.component(result.group())));
    }

}
