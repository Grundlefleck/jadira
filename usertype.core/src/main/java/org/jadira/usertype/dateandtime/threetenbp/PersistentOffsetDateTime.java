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
package org.jadira.usertype.dateandtime.threetenbp;

import java.sql.Timestamp;

import org.hibernate.usertype.ParameterizedType;
import org.jadira.usertype.dateandtime.threetenbp.columnmapper.TimestampColumnOffsetDateTimeMapper;
import org.jadira.usertype.spi.shared.AbstractVersionableUserType;
import org.jadira.usertype.spi.shared.IntegratorConfiguredType;
import org.threeten.bp.OffsetDateTime;

/**
 * Persist {@link OffsetDateTime} via Hibernate. This type is
 * mostly compatible with org.joda.time.contrib.hibernate.PersistentDateTime however
 * you should note that JodaTime's org.joda.time.DateTime has only millisecond precision,
 * whilst JSR 310 offers nanosecond precision. When interpreting nanosecond values, Joda time will
 * round down to the nearest millisecond. The type is stored using UTC timezone and presented in the
 * JVM using the JVM's default zone.
 *
 * Alternatively provide the 'databaseZone' parameter in the {@link java.util.TimeZone#getTimeZone(String)} format
 * to indicate the zone of the database. The 'javaZone' can be used to similarly configure the zone of the
 * value on return from the database.
 * N.B. To use the zone of the JVM supply 'jvm'
 */
public class PersistentOffsetDateTime extends AbstractVersionableUserType<OffsetDateTime, Timestamp, TimestampColumnOffsetDateTimeMapper> implements ParameterizedType, IntegratorConfiguredType {

    private static final long serialVersionUID = 9030422333054503794L;

    @Override
    public int compare(Object o1, Object o2) {
        return ((OffsetDateTime) o1).compareTo((OffsetDateTime) o2);
    }
}
