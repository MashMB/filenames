; Script generated by the Inno Script Studio Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Filenames"
#define MyAppVersion "1.0.0"
#define MyAppPublisher "Maciej Bedra"
#define MyAppURL "https://github.com/MashMB/filenames"
#define MyAppExeName "Filenames.exe"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)

; AppId={{GUID}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\filenames
DefaultGroupName={#MyAppName}
DisableProgramGroupPage=yes
; OutputBaseFilename=setup-filenames-1-0-0
Compression=lzma
SolidCompression=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
; Source: "[absolute path to executable]"; DestDir: "{app}"; Flags: ignoreversion
; Source: "[absolute path to logger settings]"; DestDir: "{app}"; Flags: ignoreversion
; Source: "[absolute path to CHANGELOG]"; DestDir: "{app}"; Flags: ignoreversion
; Source: "[absolute path to README]"; DestDir: "{app}"; Flags: ignoreversion
; Source: "[absolute path to LICENSE]"; DestDir: "{app}"; Flags: ignoreversion
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: desktopicon

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent