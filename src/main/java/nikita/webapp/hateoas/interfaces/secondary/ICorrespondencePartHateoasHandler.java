package nikita.webapp.hateoas.interfaces.secondary;

import nikita.common.model.noark5.v5.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v5.interfaces.entities.INoarkEntity;
import nikita.webapp.hateoas.interfaces.IHateoasHandler;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Describe Hateoas links handler
 */
public interface ICorrespondencePartHateoasHandler
        extends IHateoasHandler {

    void addRecord(INoarkEntity entity, IHateoasNoarkObject hateoasNoarkObject);

    void addCorrespondencePartType(INoarkEntity entity,
                                   IHateoasNoarkObject hateoasNoarkObject);
}
