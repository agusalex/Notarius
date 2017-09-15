package com.Notarius.view;

import com.Notarius.DashboardUI;
import com.Notarius.view.component.ProfilePreferencesWindow;

import com.Notarius.data.dto.User;
import com.Notarius.services.DashboardEvent;
import com.Notarius.services.DashboardEventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
@SuppressWarnings({ "serial", "unchecked" })
public final class DashboardMenu extends CustomComponent {

    public static final String ID = "dashboard-menu";
    public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
    public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    private Label notificationsBadge;
    private Label reportsBadge;
    private MenuItem settingsItem;

    public DashboardMenu() {
        setPrimaryStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();

        // There's only one DashboardMenu per UI so this doesn't need to be
        // unregistered from the UI-scoped DashboardEventBus.
        DashboardEventBus.register(this);

        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("Sistema <strong>Notarius</strong>",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        logoWrapper.setSpacing(false);
        return logoWrapper;
    }

    private User getCurrentUser() {
        return (User) VaadinSession.getCurrent()
                .getAttribute(User.class.getName());
    }

    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        final User user = getCurrentUser();
        settingsItem = settings.addItem("",
                new ThemeResource("img/profile-pic-300px.jpg"), null);
        updateUserName(null);
        settingsItem.addItem("Editar Perfil", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(user, false);
            }
        });
        settingsItem.addItem("Preferencias", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(user, true);
            }
        });
        settingsItem.addSeparator();
        settingsItem.addItem("Cerrar Sesion", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                DashboardEventBus.post(new DashboardEvent.UserLoggedOutEvent());
            }
        });
        return settings;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName()
                        .contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");

        for (final DashboardViewType view : DashboardViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);



            if (view == DashboardViewType.INICIO) {
                notificationsBadge = new Label();
                notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
                menuItemComponent = buildBadgeWrapper(menuItemComponent,
                        notificationsBadge);
            }



            menuItemsLayout.addComponent(menuItemComponent);
        }
        return menuItemsLayout;

    }

    private Component buildBadgeWrapper(final Component menuItemButton,
            final Component badgeLabel) {
        CssLayout dashboardWrapper = new CssLayout(menuItemButton);
        dashboardWrapper.addStyleName("badgewrapper");
        dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
        badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
        badgeLabel.setWidthUndefined();
        badgeLabel.setVisible(false);
        dashboardWrapper.addComponent(badgeLabel);
        return dashboardWrapper;
    }

    @Override
    public void attach() {
        super.attach();
        updateNotificationsCount(null);
    }

    @Subscribe
    public void postViewChange(final DashboardEvent.PostViewChangeEvent event) {
        // After a successful view change the menu can be hidden in mobile view.
        getCompositionRoot().removeStyleName(STYLE_VISIBLE);
    }

    @Subscribe
    public void updateNotificationsCount(
            final DashboardEvent.NotificationsCountUpdatedEvent event) {
        int unreadNotificationsCount = DashboardUI.getDataProvider()
                .getUnreadNotificationsCount();
        notificationsBadge.setValue(String.valueOf(unreadNotificationsCount));
        notificationsBadge.setVisible(unreadNotificationsCount > 0);
    }

    @Subscribe
    public void updateReportsCount(final DashboardEvent.ReportsCountUpdatedEvent event) {
        reportsBadge.setValue(String.valueOf(event.getCount()));
        reportsBadge.setVisible(event.getCount() > 0);
    }

    @Subscribe
    public void updateUserName(final DashboardEvent.ProfileUpdatedEvent event) {
        User user = getCurrentUser();
        settingsItem.setText(user.getFirstName() + " " + user.getLastName());
    }

    public final class ValoMenuItemButton extends Button {

        private static final String STYLE_SELECTED = "selected";

        private final DashboardViewType view;

        public ValoMenuItemButton(final DashboardViewType view) {
            this.view = view;
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            setCaption(view.getViewName().substring(0, 1).toUpperCase()
                    + view.getViewName().substring(1));
            DashboardEventBus.register(this);
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                    UI.getCurrent().getNavigator()
                            .navigateTo(view.getViewName());
                }
            });

        }

        @Subscribe
        public void postViewChange(final DashboardEvent.PostViewChangeEvent event) {
            removeStyleName(STYLE_SELECTED);
            if (event.getView() == view) {
                addStyleName(STYLE_SELECTED);
            }
        }
    }
}
