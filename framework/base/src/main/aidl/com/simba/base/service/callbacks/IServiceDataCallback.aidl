package com.simba.base.service.callbacks;

import com.simba.base.service.data.DataWrapper;

interface IServiceDataCallback {

     void onChange(in DataWrapper dataWrapper);

      boolean accept(int dataCode);

}