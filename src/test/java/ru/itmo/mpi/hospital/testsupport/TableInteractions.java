package ru.itmo.mpi.hospital.testsupport;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.Screen;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TableInteractions<E> {

    private final Table<E> table;

    public TableInteractions(Table<E> table) {
        this.table = table;
    }

    public static <E> TableInteractions<E> of(Table<E> table, Class<E> entityClass) {
        return new TableInteractions<>(table);
    }

    public static <E, S extends Screen> TableInteractions<E> of(S screen, Class<E> entityClass, String componentId) {
        return TableInteractions.of(
                (Table) screen.getWindow().getComponent(componentId),
                entityClass
        );
    }

    @Nullable
    public Button button(String buttonId) {
        return Optional.ofNullable((Button) table.getButtonsPanel().getComponent(buttonId)).orElseThrow();
    }

    public List<E> allItems() {
        return new ArrayList<>(table.getItems().getItems());
    }

    public E firstItem() {
        return table.getItems().getItems().stream().findFirst().orElseThrow();
    }

    public void selectFirst() {
        table.setSelected(firstItem());
    }

    public void selectItem(UUID itemUuid) {
        table.setSelected(table.getItems().getItem(itemUuid));
    }

    public void edit(E entity) {
        table.setSelected(entity);
        button("editBtn").click();
    }

    public void create() {
        table.getActionNN("create").actionPerform(null);
    }

    public void view(E entity) {
        table.setSelected(entity);
        button("viewBtn").click();
    }

    public void clickButton(String buttonId) {
        button(buttonId).click();
    }
}