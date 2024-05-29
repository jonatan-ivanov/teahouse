package org.example.teahouse.reporting;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {

    private final OrderService service;

    final Grid<Order> grid;

    final TextField filter;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");

    public MainView(OrderService service) {
        this.service = service;
        this.grid = new Grid<>(Order.class, false);
        this.filter = new TextField();

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter);
        add(actions, grid);

        grid.addColumn(order -> {
            Instant instant = Instant.ofEpochMilli(order.getTimestamp());
            ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
            return formatter.format(zdt);
        }).setAutoWidth(true).setHeader("Time").setSortable(true);
        grid.addColumn(Order::getTealeaf).setHeader("Tealeaf").setSortable(true);
        grid.addColumn(Order::getWater).setHeader("Water").setSortable(true);

        filter.setPlaceholder("Filter by tealeaf");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> listOrders(e.getValue()));

        // Initialize listing
        listOrders(null);
    }

    void listOrders(String filterText) {
        if (StringUtils.hasText(filterText)) {
            grid.setItems(service.getOrdersByTealeaf(filterText));
        }
        else {
            grid.setItems(service.getAll());
        }
    }
}
