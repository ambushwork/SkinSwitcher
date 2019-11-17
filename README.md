## Preview

Day mode

Night mode


## Principle

### Initialize

#### 1. Custom ViewInflater
set `CustomAppCompatViewInflater` as `Factory2` into `SkinActivity` to intercept `onCreateView` method.

#### 2. Create Custom View
When Activity is created, the custom views which implement `ViewsChange` will be created instead of native view.

#### 3. Save Attribute
During the creation of custom views, all the attribute declared in resources will be saved inside the view.

### Switch Skin

#### 1. Iterate all views
when the skin switch is fired, `SkinActivity` starts to iterate all the views from decor view,
 to check if the view is a `ViewGroup` or our custom view. if it is a `ViewGroup`, call the method recursively to continue the search,
 if it is a custom view, then we start the real skin change.

#### 2. Retrieve the resourceId
During the init, we save the resource id inside view, when skin switch is fired, we can retrieve it by the attribute key we defined.

### 3. Replace the resource
After getting the resource id, we can also get the reource **name** and **type** from the current resources,
with these two keys, we can find the alternative resource in skin package, and set it to the view.

###

## Usage
### Day/Night Mode
#### 1. Create color resource
Create `colors.xml` in res/values folder representing the day mode colors.
Create `colors.xml` in res/values-night folder as well representing the night
mode colors.

#### 2. Extends `SkinActivity`
Inherit `SkinActivity` and override `openSkin` method to return true
```
public class MainActivity extends SkinActivity{

    @Override
    public boolean openSkin() {
        return true;
    }
}
```

#### 3. Call setSkinMode to switch the skin
Inside SkinActivity, `setSkinMode` method is provided to switch the skin
```
setSkinMode(AppCompatDelegate.MODE_NIGHT_YES)

setSkinMode(AppCompatDelegate.MODE_NIGHT_NO)
````

### Switch Skin
#### 1.Create Skin package apk
the Skin package must be a standalone runnable application. color and drawable resource needs to be created to replace the default ones.
The name of the drawable or color resource must be exactly same as default view.

#### 2. Extends `SkinActivity`
Inherit `SkinActivity` and override `openSkin` method to return true
```
public class MainActivity extends SkinActivity{

    @Override
    public boolean openSkin() {
        return true;
    }
}
```

#### 3. Request Storage permission
declare storage permission in `AndroidManifest.xml`
```
 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
Ask storage permission in your Activity

#### 4. Switch skin
Skin package apk can be renamed as you want, and load it.
```
 setSkin(skinPath, R.color.defaultTextColor);
```
