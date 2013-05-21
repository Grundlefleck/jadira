/*
 *  Copyright 2010, 2011 Christopher Pheby
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.jadira.usertype.dateandtime.joda;

import org.jadira.usertype.dateandtime.joda.columnmapper.StringColumnDateTimeZoneWithOffsetMapper;
import org.jadira.usertype.dateandtime.joda.columnmapper.TimestampColumnLocalDateTimeMapper;
import org.jadira.usertype.spi.shared.ColumnMapper;
import org.jadira.usertype.spi.utils.reflection.ArrayUtils;

/**
 * Persist {@link org.joda.time.DateTime} via Hibernate. The offset will be stored in an extra column.
 * @deprecated This class is being replaced by {@link PersistentDateTimeAndZoneWithOffset}. The handling
 * of Date / Time Zone boundaries is slightly different so you are encouraged to retest before migrating
 * to this class
 */
@Deprecated
public class PersistentDateTimeWithZone extends AbstractMultiColumnDateTime {

    private static final long serialVersionUID = 1364221029392346011L;

    private static final ColumnMapper<?, ?>[] columnMappers = new ColumnMapper<?, ?>[] { new TimestampColumnLocalDateTimeMapper(), new StringColumnDateTimeZoneWithOffsetMapper() };

    private static final String[] propertyNames = new String[]{ "datetime", "offset" };

    @Override
    protected ColumnMapper<?, ?>[] getColumnMappers() {
        return columnMappers;
    }

    @Override
    public String[] getPropertyNames() {
        return ArrayUtils.copyOf(propertyNames);
    }
}
