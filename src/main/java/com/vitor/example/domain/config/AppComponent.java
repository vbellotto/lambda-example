package com.vitor.example.domain.config;

import com.vitor.example.domain.service.ItemService;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger 2 App Component.
 *
 * Is an interface that Dagger will use to generate the code that will do the dependency injection for you.
 *
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    /**
     * Get the ItemService.
     * @return - the {@link ItemService}
     */
    ItemService getItemService();
}
