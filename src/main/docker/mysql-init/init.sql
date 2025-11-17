
    create table conditions (
        diagnosedDate date not null,
        severityLevel integer not null,
        conditionId binary(16) not null,
        patient_id binary(16) not null,
        practitioner_id binary(16) not null,
        conditionName varchar(255) not null,
        conditionType enum ('Autoimmune','Cancerous','Chronic','Genetic','Infectious','Neurological','Psychiatric') not null,
        primary key (conditionId)
    ) engine=InnoDB;

    create table encounters (
        encounterDate datetime(6) not null,
        encounterId binary(16) not null,
        patient_id binary(16) not null,
        practitioner_id binary(16) not null,
        description varchar(255) not null,
        primary key (encounterId)
    ) engine=InnoDB;

    create table locations (
        locationId binary(16) not null,
        locationType enum ('Danderyd','Huddinge','Kungsholmen','Sodermalm','Solna') not null,
        primary key (locationId)
    ) engine=InnoDB;

    create table messages (
        dateTime datetime(6) not null,
        message_id binary(16) not null,
        sender_id binary(16) not null,
        session_id binary(16) not null,
        message TEXT not null,
        primary key (message_id)
    ) engine=InnoDB;

    create table observations (
        observationDate datetime(6) not null,
        observationId binary(16) not null,
        patient_id binary(16) not null,
        practitioner_id binary(16) not null,
        description varchar(255) not null,
        primary key (observationId)
    ) engine=InnoDB;

    create table organizations (
        location_id binary(16) not null,
        organizationId binary(16) not null,
        organizationType enum ('DanderydsSjukhus','KarolinskaUniversitetssjukhuset','LillaErstagarden','Sodersjukhuset','StGoransSjukhus') not null,
        primary key (organizationId)
    ) engine=InnoDB;

    create table sessions (
        creationDate datetime(6) not null,
        receiver_id binary(16) not null,
        sender_id binary(16) not null,
        sessionId binary(16) not null,
        subject varchar(255) not null,
        primary key (sessionId)
    ) engine=InnoDB;

    create table users (
        id binary(16) not null,
        organization_id binary(16) not null,
        practitioner_id binary(16) not null,
        user_role varchar(31) not null,
        email varchar(255) not null,
        fullName varchar(255) not null,
        password varchar(255) not null,
        userType enum ('Doctor','OtherStaff','Patient') not null,
        primary key (id),
        check ((user_role in ('PRACTITIONER','PATIENT')))
    ) engine=InnoDB;

    alter table organizations 
       add constraint UKg0kfbrg9bob3le5fso86b7ebj unique (location_id);

    alter table users 
       add constraint UK6dotkott2kjsp8vw4d0m25fb7 unique (email);

    alter table conditions 
       add constraint FK5dnuk08yxv4e3ky0gnpbpo1t3 
       foreign key (patient_id) 
       references users (id);

    alter table conditions 
       add constraint FKxgpix71i5f8m5df7bia2g608 
       foreign key (practitioner_id) 
       references users (id);

    alter table encounters 
       add constraint FK1pkkrmb5o0yuvx29srqmgmiuo 
       foreign key (patient_id) 
       references users (id);

    alter table encounters 
       add constraint FKf773yf10kos7phrxt6n5ftvd7 
       foreign key (practitioner_id) 
       references users (id);

    alter table messages 
       add constraint FK4ui4nnwntodh6wjvck53dbk9m 
       foreign key (sender_id) 
       references users (id);

    alter table messages 
       add constraint FKtkbtam456hs6b6y3d81c08rpx 
       foreign key (session_id) 
       references sessions (sessionId);

    alter table observations 
       add constraint FKsf34347604wlc7amo427uy7y3 
       foreign key (patient_id) 
       references users (id);

    alter table observations 
       add constraint FKclauoqcstc94jx4lce4e4vety 
       foreign key (practitioner_id) 
       references users (id);

    alter table organizations 
       add constraint FKkvfsnlewse3pfw7qexip9mdx4 
       foreign key (location_id) 
       references locations (locationId);

    alter table sessions 
       add constraint FK5c4h72u8yp057wgocltutmbog 
       foreign key (receiver_id) 
       references users (id);

    alter table sessions 
       add constraint FKd3lfxgt5f804ci22bvl8dwote 
       foreign key (sender_id) 
       references users (id);

    alter table users 
       add constraint FKqpugllwvyv37klq7ft9m8aqxk 
       foreign key (organization_id) 
       references organizations (organizationId);

    alter table users 
       add constraint FKib9ddlfaunnrgjnua88owhglg 
       foreign key (practitioner_id) 
       references organizations (organizationId);
